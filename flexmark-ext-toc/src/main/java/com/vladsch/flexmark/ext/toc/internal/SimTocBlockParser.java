package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.ext.toc.SimTocOption;
import com.vladsch.flexmark.ext.toc.SimTocOptions;
import com.vladsch.flexmark.internal.util.Pair;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.HtmlBlock;
import com.vladsch.flexmark.node.ListBlock;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.block.BlockStart.none;

public class SimTocBlockParser extends AbstractBlockParser {
    private static Pattern TOC_BLOCK_START = Pattern.compile("^\\[TOC(?:\\s+([^\\]]+))?]:\\s*#(?:\\s+(\"(?:.*)\"))?\\s*$");
    //private static Pattern TOC_BLOCK_CONTINUE = Pattern.compile("^.+$");

    private static int HAVE_HTML = 1;
    private static int HAVE_HEADING = 2;
    private static int HAVE_LIST = 4;

    private final SimTocBlock block;
    //private BlockContent content = new BlockContent();
    private final TocOptions options;
    private int haveChildren = 0;

    private SimTocBlockParser(DataHolder options, BasedSequence tocChars, BasedSequence styleChars, BasedSequence titleChars) {
        this.options = new TocOptions(options);
        block = new SimTocBlock(tocChars, styleChars, titleChars);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // we stop on a blank line
        return state.isBlank() ? BlockContinue.none() : BlockContinue.atIndex(state.getIndex());
    }

    @Override
    public boolean canContain(Block block) {
        if (block instanceof HtmlBlock) {
            if (haveChildren == 0) {
                haveChildren |= HAVE_HTML;
                return true;
            }
        } else if (block instanceof Heading) {
            if (haveChildren == 0) {
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
    public void closeBlock(ParserState parserState) {
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
            block.appendChild(tocContent);
            block.setCharsFromContent();
            parserState.blockAddedWithChildren(tocContent);
        }

        // now add the options list and options with their text

        if (!block.getStyle().isEmpty()) {
            SimTocOptionsParser optionsParser = new SimTocOptionsParser();
            Pair<TocOptions, List<ParsedOption<TocOptions>>> pair = optionsParser.parseOption(block.getStyle(), TocOptions.DEFAULT, null);
            List<ParsedOption<TocOptions>> options = pair.getSecond();
            if (!options.isEmpty()) {
                // add these
                SimTocOptions optionsNode = new SimTocOptions();
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
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final TocOptions options;

        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new TocOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4) {
                return BlockStart.none();
            }
            BasedSequence line = state.getLine();
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
            Matcher matcher = TOC_BLOCK_START.matcher(line);
            if (matcher.matches()) {
                BasedSequence tocChars = state.getLineWithEOL();
                BasedSequence styleChars = null;
                BasedSequence titleChars = null;
                if (matcher.start(1) != -1) {
                    int styleStart = matcher.start(1);
                    int styleEnd = matcher.end(1);
                    styleChars = trySequence.subSequence(styleStart, styleEnd);
                }

                if (matcher.start(2) != -1) {
                    int titleStart = matcher.start(1);
                    int titleEnd = matcher.end(1);
                    titleChars = trySequence.subSequence(titleStart, titleEnd);
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
