package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.*;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.test.util.spec.SpecReader.EXAMPLE_KEYWORD;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

public class SpecExampleBlockParser extends AbstractBlockParser {
    final private static Pattern OPTIONS_PATTERN = Pattern.compile("^\\s*(\\()?([^:()]*)(?:(:)\\s*([^\\s()]+)\\s*?)?(\\))?(?:\\s+(options)\\s*(\\()?([^()\\n\\r]*)(\\))?)?\\s*$");
    final private static int GROUP_COORD_OPEN = 1;
    final private static int GROUP_SECTION = 2;
    final private static int GROUP_NUMBER_SEPARATOR = 3;
    final private static int GROUP_NUMBER = 4;
    final private static int GROUP_COORD_CLOSE = 5;
    final private static int GROUP_OPTION_KEYWORD = 6;
    final private static int GROUP_OPTIONS_OPEN = 7;
    final private static int GROUP_OPTIONS = 8;
    final private static int GROUP_OPTIONS_CLOSE = 9;

    final SpecExampleBlock block = new SpecExampleBlock();
    private BlockContent content = new BlockContent();
    final private SpecExampleOptions myOptions;

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
        content.add(line, 0);
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
                BasedSequence coordOpeningMarker = NULL;
                BasedSequence section = NULL;
                BasedSequence numberSeparator = NULL;
                BasedSequence number = NULL;
                BasedSequence coordClosingMarker = NULL;
                BasedSequence optionsKeyword = NULL;
                BasedSequence optionsOpeningMarker = NULL;
                BasedSequence optionsText = NULL;
                BasedSequence optionsClosingMarker = NULL;
                // @formatter:off
                if (options.group(GROUP_COORD_OPEN) != null && !options.group(GROUP_COORD_OPEN).trim().isEmpty()){coordOpeningMarker = optionsChars.subSequence(options.start(GROUP_COORD_OPEN), options.end(GROUP_COORD_OPEN)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_SECTION) != null && !options.group(GROUP_SECTION).trim().isEmpty()){section = optionsChars.subSequence(options.start(GROUP_SECTION), options.end(GROUP_SECTION)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_NUMBER_SEPARATOR) != null && !options.group(GROUP_NUMBER_SEPARATOR).trim().isEmpty()){numberSeparator = optionsChars.subSequence(options.start(GROUP_NUMBER_SEPARATOR), options.end(GROUP_NUMBER_SEPARATOR)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_NUMBER) != null && !options.group(GROUP_NUMBER).trim().isEmpty()){number = optionsChars.subSequence(options.start(GROUP_NUMBER), options.end(GROUP_NUMBER)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_COORD_CLOSE) != null && !options.group(GROUP_COORD_CLOSE).trim().isEmpty()){coordClosingMarker = optionsChars.subSequence(options.start(GROUP_COORD_CLOSE), options.end(GROUP_COORD_CLOSE)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_OPTION_KEYWORD) != null && !options.group(GROUP_OPTION_KEYWORD).trim().isEmpty()){optionsKeyword = optionsChars.subSequence(options.start(GROUP_OPTION_KEYWORD), options.end(GROUP_OPTION_KEYWORD)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_OPTIONS_OPEN) != null && !options.group(GROUP_OPTIONS_OPEN).trim().isEmpty()){optionsOpeningMarker = optionsChars.subSequence(options.start(GROUP_OPTIONS_OPEN), options.end(GROUP_OPTIONS_OPEN)).trim(CharPredicate.WHITESPACE_NBSP);}
                if (options.group(GROUP_OPTIONS) != null){optionsText = optionsChars.subSequence(options.start(GROUP_OPTIONS), options.end(GROUP_OPTIONS));}
                if (options.group(GROUP_OPTIONS_CLOSE) != null && !options.group(GROUP_OPTIONS_CLOSE).trim().isEmpty()){optionsClosingMarker = optionsChars.subSequence(options.start(GROUP_OPTIONS_CLOSE), options.end(GROUP_OPTIONS_CLOSE)).trim(CharPredicate.WHITESPACE_NBSP);}
                // @formatter:on
                if (section.isNotNull() && optionsKeyword.isNull() && numberSeparator.isNull() && coordOpeningMarker.isNull() && section.matchChars("options")) {
                    // move all from section to options
                    int pos = section.indexOfAny(CharPredicate.SPACE_TAB_NBSP);
                    if (pos < 0) {
                        optionsKeyword = section;
                    } else {
                        optionsKeyword = section.subSequence(0, pos);
                        optionsText = section.subSequence(pos + 1);
                    }
                    optionsClosingMarker = coordClosingMarker;
                    section = NULL;
                    coordClosingMarker = NULL;
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
                BasedSequence trimmedOptionsList = block.getOptions().trim(CharPredicate.WHITESPACE_NBSP);
                if (!trimmedOptionsList.isEmpty()) {
                    BasedSequence[] list = trimmedOptionsList.split(",", 0, SequenceUtils.SPLIT_INCLUDE_DELIM_PARTS);
                    for (BasedSequence item : list) {
                        BasedSequence option = item.trim(CharPredicate.WHITESPACE_NBSP);
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
                int sectionStart = -1;
                BasedSequence lastLine = lines.get(lines.size() - 1);
                String typeBreak = myOptions.sectionBreak;
                int typeBreakLength = typeBreak.length();

                for (BasedSequence line : lines.subList(1, lines.size())) {
                    if (line.length() == typeBreakLength + line.countTrailing(CharPredicate.ANY_EOL) && line.matchChars(typeBreak)) {
                        if (inSource) {
                            inSource = false;
                            if (sectionStart != -1) {
                                block.setSource(line.baseSubSequence(sectionStart, line.getStartOffset()));
                            } else {
                                block.setSource(line.subSequence(0, 0));
                            }
                            block.setHtmlSeparator(line);
                            inHtml = true;
                            sectionStart = -1;
                        } else if (inHtml) {
                            inHtml = false;
                            if (sectionStart != -1) {
                                block.setHtml(line.baseSubSequence(sectionStart, line.getStartOffset()));
                            } else {
                                block.setHtml(line.subSequence(0, 0));
                            }
                            block.setAstSeparator(line);
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

                    if (line == lastLine) {
                        // done
                        if (inSource) {
                            if (sectionStart != -1) {
                                block.setSource(line.baseSubSequence(sectionStart, line.getEndOffset()));
                            } else {
                                block.setSource(line.subSequence(line.length(), line.length()));
                            }
                        } else if (inHtml) {
                            if (sectionStart != -1) {
                                block.setHtml(line.baseSubSequence(sectionStart, line.getEndOffset()));
                            } else {
                                block.setHtml(line.subSequence(line.length(), line.length()));
                            }
                        } else {
                            if (sectionStart != -1) {
                                block.setAst(line.baseSubSequence(sectionStart, line.getEndOffset()));
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
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class
            ));
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
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

        @NotNull
        @Override
        public BlockParserFactory apply(@NotNull DataHolder options) {
            return new SpecExampleBlockParser.BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        final private SpecExampleOptions myOptions;

        BlockFactory(DataHolder options) {
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
