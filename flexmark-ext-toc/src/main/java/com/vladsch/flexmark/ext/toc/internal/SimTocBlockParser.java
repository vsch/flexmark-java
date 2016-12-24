package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.ext.toc.SimTocOption;
import com.vladsch.flexmark.ext.toc.SimTocOptionList;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.ParsedOption;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.block.BlockStart.none;

public class SimTocBlockParser extends AbstractBlockParser {
    static class TocParsing extends Parsing {
        final Pattern TOC_BLOCK_START;
        //private static Pattern TOC_BLOCK_CONTINUE = Pattern.compile("^.+$");

        public TocParsing(DataHolder options) {
            super(options);
            this.TOC_BLOCK_START = Pattern.compile("^\\[TOC(?:\\s+([^\\]]+))?]:\\s*#(?:\\s+(" + super.LINK_TITLE_STRING + "))?\\s*$");
        }
    }

    static int HAVE_HTML = 1;
    static int HAVE_HEADING = 2;
    static int HAVE_LIST = 4;
    static int HAVE_BLANK_LINE = 8;

    private final SimTocBlock block;
    //private BlockContent content = new BlockContent();
    private final TocOptions options;
    private int haveChildren = 0;
    private BasedSequence blankLineSpacer = BasedSequence.NULL;

    SimTocBlockParser(DataHolder options, BasedSequence tocChars, BasedSequence styleChars, BasedSequence titleChars) {
        this.options = new TocOptions(options);
        block = new SimTocBlock(tocChars, styleChars, titleChars);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // we stop on a blank line if blank line spacer is not enabled or we already had one
        if ((!options.isBlankLineSpacer || haveChildren != 0) && state.isBlank()) {
            return BlockContinue.none();
        } else {
            if (state.isBlank()) {
                haveChildren |= HAVE_BLANK_LINE;
                blankLineSpacer = state.getLine();
            }
            return BlockContinue.atIndex(state.getIndex());
        }
    }

    @Override
    public boolean canContain(Block block) {
        if (block instanceof HtmlBlock) {
            if ((haveChildren & ~HAVE_BLANK_LINE) == 0) {
                haveChildren |= HAVE_HTML;
                return true;
            }
        } else if (block instanceof Heading) {
            if ((haveChildren & ~HAVE_BLANK_LINE) == 0) {
                haveChildren |= HAVE_HEADING;
                return true;
            }
        } else if (block instanceof ListBlock) {
            if ((haveChildren & (HAVE_HTML | HAVE_LIST)) == 0) {
                haveChildren |= HAVE_LIST;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        //content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState state) {
        //block.setContent(content);
        //if (block.getLineCount() > 1) {
        //    BasedSequence contentChars = SegmentedSequence.of(block.getContentLines(1, block.getLineCount()), block.getChars());
        //    if (!contentChars.isEmpty()) {
        //        SimTocContent node = new SimTocContent(contentChars);
        //        block.appendChild(node);
        //    }
        //}
        if (block.hasChildren()) {
            // move the children to a SimTocContent node
            SimTocContent tocContent = new SimTocContent();
            tocContent.takeChildren(block);
            tocContent.setCharsFromContent();

            if (blankLineSpacer.isNotNull()) {
                // need to extend the content node start to include the blank line
                tocContent.setChars(Node.spanningChars(blankLineSpacer, tocContent.getChars()));
            }

            block.appendChild(tocContent);
            block.setCharsFromContent();
            state.blockAddedWithChildren(tocContent);
        }

        // now add the options list and options with their text

        if (options.isAstAddOptions && !block.getStyle().isEmpty()) {
            SimTocOptionsParser optionsParser = new SimTocOptionsParser();
            Pair<TocOptions, List<ParsedOption<TocOptions>>> pair = optionsParser.parseOption(block.getStyle(), TocOptions.DEFAULT, null);
            List<ParsedOption<TocOptions>> options = pair.getSecond();
            if (!options.isEmpty()) {
                // add these
                SimTocOptionList optionsNode = new SimTocOptionList();
                for (ParsedOption<TocOptions> option : options) {
                    SimTocOption optionNode = new SimTocOption(option.getSource());
                    optionsNode.appendChild(optionNode);
                }

                optionsNode.setCharsFromContent();
                block.prependChild(optionsNode);
            }
        }

        block.setCharsFromContent();
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final TocOptions options;
        private final TocParsing myParsing;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new TocOptions(options);
            this.myParsing = new TocParsing(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4) {
                return BlockStart.none();
            }
            BasedSequence line = state.getLine();
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
            Matcher matcher = myParsing.TOC_BLOCK_START.matcher(line);
            if (matcher.matches()) {
                BasedSequence tocChars = state.getLineWithEOL();
                BasedSequence styleChars = null;
                BasedSequence titleChars = null;
                if (matcher.start(1) != -1) {
                    styleChars = trySequence.subSequence(matcher.start(1), matcher.end(1));
                }

                if (matcher.start(2) != -1) {
                    titleChars = trySequence.subSequence(matcher.start(2), matcher.end(2));
                }

                SimTocBlockParser simTocBlockParser = new SimTocBlockParser(state.getProperties(), tocChars, styleChars, titleChars);
                return BlockStart.of(simTocBlockParser)
                        .atIndex(state.getLineEndIndex() + state.getLineEolLength())
                        //.replaceActiveBlockParser()
                        ;
            }
            return none();
        }
    }
}
