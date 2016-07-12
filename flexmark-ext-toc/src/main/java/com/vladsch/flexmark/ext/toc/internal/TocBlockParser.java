package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.block.BlockStart.none;

public class TocBlockParser extends AbstractBlockParser {
    private static Pattern TOC_BLOCK_START = Pattern.compile("^\\[TOC(?:\\s+([^\\]]+))?]\\s*$");
    private static Pattern TOC_BLOCK_CONTINUE = Pattern.compile("");

    private final TocBlock block;
    //private BlockContent content = new BlockContent();
    private final TocOptions options;

    private TocBlockParser(DataHolder options, BasedSequence tocChars, BasedSequence styleChars) {
        this.options = new TocOptions(options);
        block = new TocBlock(tocChars, styleChars);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        return BlockContinue.none();
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        //content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState parserState) {
        //block.setContent(content);
        //content = null;
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

                TocBlockParser tocBlockParser = new TocBlockParser(state.getProperties(), tocChars, styleChars);
                return BlockStart.of(tocBlockParser)
                        .atIndex(state.getIndex())
                        //.replaceActiveBlockParser()
                        ;
            }
            return none();
        }
    }
}
