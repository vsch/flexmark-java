package com.vladsch.flexmark.ext.simtoc.internal;

import com.vladsch.flexmark.ext.simtoc.SimTocBlock;
import com.vladsch.flexmark.ext.simtoc.SimTocContent;
import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SegmentedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.block.BlockStart.none;

public class SimTocBlockParser extends AbstractBlockParser {
    private static Pattern TOC_BLOCK_START = Pattern.compile("^\\[TOC(?:\\s+([^\\]]+))?]:\\s*#(?:\\s+(\"(?:.*)\"))?\\s*$");
    //private static Pattern TOC_BLOCK_CONTINUE = Pattern.compile("^.+$");

    private final SimTocBlock block;
    private BlockContent content = new BlockContent();
    private final SimTocOptions options;

    private SimTocBlockParser(DataHolder options, BasedSequence tocChars, BasedSequence styleChars, BasedSequence titleChars) {
        this.options = new SimTocOptions(options);
        block = new SimTocBlock(tocChars, styleChars, titleChars);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // we stop on a blank line
        return state.isBlank() ? BlockContinue.finished() : BlockContinue.atIndex(state.getIndex());
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setContent(content);
        if (block.getLineCount() > 1) {
            BasedSequence contentChars = SegmentedSequence.of(block.getContentLines(1, block.getLineCount()), block.getChars());
            if (!contentChars.isEmpty()) {
                SimTocContent node = new SimTocContent(contentChars);
                block.appendChild(node);
            }
        }
        block.setCharsFromContent();
        content = null;
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
        private final SimTocOptions options;

        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new SimTocOptions(options);
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
                int openingStart = matcher.start();
                int openingEnd = matcher.end();
                BasedSequence tocChars = trySequence.subSequence(openingStart, openingEnd).trimEnd();
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
                        .atIndex(state.getIndex())
                        //.replaceActiveBlockParser()
                        ;
            }
            return none();
        }
    }
}
