package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.*;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.spec.SpecReader.EXAMPLE_KEYWORD;
import static com.vladsch.flexmark.spec.SpecReader.OPTIONS_KEYWORD;
import static com.vladsch.flexmark.util.sequence.BasedSequence.SPLIT_INCLUDE_DELIM_PARTS;
import static com.vladsch.flexmark.util.sequence.BasedSequence.WHITESPACE_NBSP_CHARS;

public class SpecExampleBlockParser extends AbstractBlockParser {
    private static final Pattern OPTIONS_PATTERN = Pattern.compile("^\\s*(\\()?([^:()]*)(?:(:)\\s*([^\\s()]+)\\s*?)?(\\))?(?:\\s+(options)\\s*(\\()?([^()\\n\\r]*)(\\))?)?\\s*$".replace("options", OPTIONS_KEYWORD));
    private static final int GROUP_COORD_OPEN = 1;
    private static final int GROUP_SECTION = 2;
    private static final int GROUP_NUMBER_SEPARATOR = 3;
    private static final int GROUP_NUMBER = 4;
    private static final int GROUP_COORD_CLOSE = 5;
    private static final int GROUP_OPTION_KEYWORD = 6;
    private static final int GROUP_OPTIONS_OPEN = 7;
    private static final int GROUP_OPTIONS = 8;
    private static final int GROUP_OPTIONS_CLOSE = 9;

    private final SpecExampleBlock block = new SpecExampleBlock();
    private BlockContent content = new BlockContent();
    private final SpecExampleOptions myOptions;

    public SpecExampleBlockParser(DataHolder options) {
        myOptions = new SpecExampleOptions(options);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        BasedSequence line = state.getLine();
        if (line.startsWith(myOptions.exampleBreak)) {
            block.setClosingMarker(line.subSequence(0, myOptions.exampleBreak.length()));
            return BlockContinue.finished();
        }
        return BlockContinue.atIndex(0);
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public boolean isPropagatingLastBlankLine(BlockParser lastMatchedBlockParser) {
        return false;
    }

    @Override
    public void closeBlock(ParserState state) {
        // first line, if not blank, has the info string
        List<BasedSequence> lines = content.getLines();
        if (lines.size() > 0) {
            BasedSequence info = lines.get(0);

            int exampleKeyWordStart = myOptions.exampleBreak.length() + 1;
            int exampleKeyWordEnd = exampleKeyWordStart + EXAMPLE_KEYWORD.length();
            BasedSequence exampleKeyword = info.subSequence(exampleKeyWordStart, exampleKeyWordEnd);
            BasedSequence optionsChars = info.subSequence(exampleKeyWordEnd);
            Matcher options = OPTIONS_PATTERN.matcher(optionsChars.toString().replace('\u00A0', ' '));

            block.setOpeningMarker(info.subSequence(0, myOptions.exampleBreak.length()));
            block.setExampleKeyword(exampleKeyword);

            if (options.matches()) {
                BasedSequence coordOpeningMarker = BasedSequence.NULL;
                BasedSequence section = BasedSequence.NULL;
                BasedSequence numberSeparator = BasedSequence.NULL;
                BasedSequence number = BasedSequence.NULL;
                BasedSequence coordClosingMarker = BasedSequence.NULL;
                BasedSequence optionsKeyword = BasedSequence.NULL;
                BasedSequence optionsOpeningMarker = BasedSequence.NULL;
                BasedSequence optionsText = BasedSequence.NULL;
                BasedSequence optionsClosingMarker = BasedSequence.NULL;
                // @formatter:off
                if (options.group(GROUP_COORD_OPEN) != null && !options.group(GROUP_COORD_OPEN).trim().isEmpty()){coordOpeningMarker = optionsChars.subSequence(options.start(GROUP_COORD_OPEN), options.end(GROUP_COORD_OPEN)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_SECTION) != null && !options.group(GROUP_SECTION).trim().isEmpty()){section = optionsChars.subSequence(options.start(GROUP_SECTION), options.end(GROUP_SECTION)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_NUMBER_SEPARATOR) != null && !options.group(GROUP_NUMBER_SEPARATOR).trim().isEmpty()){numberSeparator = optionsChars.subSequence(options.start(GROUP_NUMBER_SEPARATOR), options.end(GROUP_NUMBER_SEPARATOR)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_NUMBER) != null && !options.group(GROUP_NUMBER).trim().isEmpty()){number = optionsChars.subSequence(options.start(GROUP_NUMBER), options.end(GROUP_NUMBER)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_COORD_CLOSE) != null && !options.group(GROUP_COORD_CLOSE).trim().isEmpty()){coordClosingMarker = optionsChars.subSequence(options.start(GROUP_COORD_CLOSE), options.end(GROUP_COORD_CLOSE)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_OPTION_KEYWORD) != null && !options.group(GROUP_OPTION_KEYWORD).trim().isEmpty()){optionsKeyword = optionsChars.subSequence(options.start(GROUP_OPTION_KEYWORD), options.end(GROUP_OPTION_KEYWORD)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_OPTIONS_OPEN) != null && !options.group(GROUP_OPTIONS_OPEN).trim().isEmpty()){optionsOpeningMarker = optionsChars.subSequence(options.start(GROUP_OPTIONS_OPEN), options.end(GROUP_OPTIONS_OPEN)).trim(WHITESPACE_NBSP_CHARS);}
                if (options.group(GROUP_OPTIONS) != null){optionsText = optionsChars.subSequence(options.start(GROUP_OPTIONS), options.end(GROUP_OPTIONS));}
                if (options.group(GROUP_OPTIONS_CLOSE) != null && !options.group(GROUP_OPTIONS_CLOSE).trim().isEmpty()){optionsClosingMarker = optionsChars.subSequence(options.start(GROUP_OPTIONS_CLOSE), options.end(GROUP_OPTIONS_CLOSE)).trim(WHITESPACE_NBSP_CHARS);}
                // @formatter:on
                if (section.isNotNull() && optionsKeyword.isNull() && numberSeparator.isNull() && coordOpeningMarker.isNull() && section.matchChars("options")) {
                    // move all from section to options
                    int pos = section.indexOfAny(' ', '\t', '\u00A0');
                    if (pos < 0) {
                        optionsKeyword = section;
                    } else {
                        optionsKeyword = section.subSequence(0, pos);
                        optionsText = section.subSequence(pos + 1);
                    }
                    optionsClosingMarker = coordClosingMarker;
                    section = BasedSequence.NULL;
                    coordClosingMarker = BasedSequence.NULL;
                }

                if (optionsText.isNull()) {
                    if (optionsClosingMarker.isNotNull()) {
                        optionsText = optionsClosingMarker.subSequence(0, 0);
                    } else if (optionsOpeningMarker.isNotNull()) {
                        optionsText = optionsOpeningMarker.subSequence(1, 1);
                    } else if (optionsKeyword.isNotNull()) {
                        optionsText = optionsKeyword.subSequence(optionsKeyword.length(), optionsKeyword.length());
                    }
                }

                block.setCoordOpeningMarker(coordOpeningMarker);
                block.setSection(section);
                block.setNumberSeparator(numberSeparator);
                block.setNumber(number);
                block.setCoordClosingMarker(coordClosingMarker);
                block.setOptionsKeyword(optionsKeyword);
                block.setOptionsOpeningMarker(optionsOpeningMarker);
                block.setOptions(optionsText);
                block.setOptionsClosingMarker(optionsClosingMarker);
            }

            // if we create option nodes, we break up the options
            if (myOptions.optionNodes && block.getOptionsKeyword().isNotNull()) {
                Node optionsList = new SpecExampleOptionsList(block.getOptions());
                block.appendChild(optionsList);
                BasedSequence trimmedOptionsList = block.getOptions().trim(WHITESPACE_NBSP_CHARS);
                if (!trimmedOptionsList.isEmpty()) {
                    BasedSequence[] list = trimmedOptionsList.split(',', 0, SPLIT_INCLUDE_DELIM_PARTS);
                    for (BasedSequence item : list) {
                        BasedSequence option = item.trim(WHITESPACE_NBSP_CHARS);
                        if (!option.isEmpty()) {
                            if (option.matches(",")) {
                                Node optionNode = new SpecExampleOptionSeparator(option);
                                optionsList.appendChild(optionNode);
                            } else {
                                Node optionNode = new SpecExampleOption(option);
                                optionsList.appendChild(optionNode);
                            }
                        }
                    }
                }
            }

            BasedSequence chars = content.getSpanningChars();
            BasedSequence spanningChars = chars.baseSubSequence(chars.getStartOffset(), lines.get(0).getEndOffset());

            if (lines.size() > 1) {
                // have more lines
                block.setContent(spanningChars, lines.subList(1, lines.size()));

                // need to find the parts
                boolean inSource = true;
                boolean inHtml = false;
                boolean inAst = false;
                int sectionStart = -1;
                BasedSequence prevLine = BasedSequence.NULL;
                BasedSequence lastLine = lines.get(lines.size() - 1);
                String typeBreak = myOptions.typeBreak;
                int typeBreakLength = typeBreak.length();

                for (BasedSequence line : lines.subList(1, lines.size())) {
                    if (line.length() == typeBreakLength + line.countTrailing(BasedSequence.EOL_CHARS) && line.matchChars(typeBreak)) {
                        if (inSource) {
                            inSource = false;
                            if (sectionStart != -1) {
                                block.setSource(line.baseSubSequence(sectionStart, line.getStartOffset() - prevLine.countTrailing(BasedSequence.EOL_CHARS)));
                            } else {
                                block.setSource(line.subSequence(0, 0));
                            }
                            block.setHtmlSeparator(line);
                            inHtml = true;
                            sectionStart = -1;
                        } else if (inHtml) {
                            inHtml = false;
                            if (sectionStart != -1) {
                                block.setHtml(line.baseSubSequence(sectionStart, line.getStartOffset() - prevLine.countTrailing(BasedSequence.EOL_CHARS)));
                            } else {
                                block.setHtml(line.subSequence(0, 0));
                            }
                            block.setAstSeparator(line);
                            inAst = true;
                            sectionStart = -1;
                        } else {
                            if (sectionStart == -1) {
                                sectionStart = line.getStartOffset();
                            }
                        }
                    } else {
                        if (sectionStart == -1) {
                            sectionStart = line.getStartOffset();
                        }
                    }

                    prevLine = line;

                    if (line == lastLine) {
                        // done
                        if (inSource) {
                            if (sectionStart != -1) {
                                block.setSource(line.baseSubSequence(sectionStart, line.getEndOffset() - prevLine.countTrailing(BasedSequence.EOL_CHARS)));
                            } else {
                                block.setSource(line.subSequence(line.length(), line.length()));
                            }
                        } else if (inHtml) {
                            if (sectionStart != -1) {
                                block.setHtml(line.baseSubSequence(sectionStart, line.getEndOffset() - prevLine.countTrailing(BasedSequence.EOL_CHARS)));
                            } else {
                                block.setHtml(line.subSequence(line.length(), line.length()));
                            }
                        } else if (inAst) {
                            if (sectionStart != -1) {
                                block.setAst(line.baseSubSequence(sectionStart, line.getEndOffset() - prevLine.countTrailing(BasedSequence.EOL_CHARS)));
                            } else {
                                block.setAst(line.subSequence(line.length(), line.length()));
                            }
                        }

                        break;
                    }
                }

                // here if we create section nodes
                if (block.getSource().isNotNull()) {
                    Node node = new SpecExampleSource(block.getSource());
                    block.appendChild(node);
                }

                if (block.getHtmlSeparator().isNotNull()) {
                    Node node = new SpecExampleSeparator(block.getHtmlSeparator());
                    block.appendChild(node);

                    if (block.getHtml().isNotNull()) {
                        node = new SpecExampleHtml(block.getHtml());
                        block.appendChild(node);
                    }

                    if (block.getAstSeparator().isNotNull()) {
                        node = new SpecExampleSeparator(block.getAstSeparator());
                        block.appendChild(node);
                        if (block.getAst().isNotNull()) {
                            node = new SpecExampleAst(block.getAst());
                            block.appendChild(node);
                        }
                    }
                }
            } else {
                Node node = new SpecExampleSource(block.getClosingMarker().subSequence(0, 0));
                block.appendChild(node);
                block.setContent(spanningChars, BasedSequence.EMPTY_LIST);
            }
        } else {
            Node node = new SpecExampleSource(block.getClosingMarker().subSequence(0, 0));
            block.appendChild(node);
            block.setContent(content);
        }

        block.setCharsFromContent();
        content = null;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class
                    //FencedCodeBlockParser.Factory.class
                    //HtmlBlockParser.Factory.class,
                    //ThematicBreakParser.Factory.class,
                    //ListBlockParser.Factory.class,
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    //HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class,
                    HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class,
                    IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory apply(DataHolder options) {
            return new SpecExampleBlockParser.BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final SpecExampleOptions myOptions;

        private BlockFactory(DataHolder options) {
            super(options);
            myOptions = new SpecExampleOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            if (state.getIndex() == 0) {
                int breakLength = myOptions.exampleBreak.length();
                if (line.length() >= breakLength + 1 + EXAMPLE_KEYWORD.length() && line.startsWith(myOptions.exampleBreak) && line.matchChars(EXAMPLE_KEYWORD, breakLength + 1) && " \t\u00A0".contains(String.valueOf(line.charAt(breakLength)))) {
                    SpecExampleBlockParser blockParser = new SpecExampleBlockParser(state.getProperties());
                    blockParser.block.setOpeningMarker(line.subSequence(0, breakLength));
                    //blockParser.addLine(state, state.getLineWithEOL());
                    return BlockStart.of(blockParser).atIndex(-1);
                }
            }
            return BlockStart.none();
        }
    }
}
