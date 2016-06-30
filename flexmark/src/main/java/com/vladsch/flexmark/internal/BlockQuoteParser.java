package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.Parsing;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.BlockQuote;
import com.vladsch.flexmark.parser.block.*;

public class BlockQuoteParser extends AbstractBlockParser {

    private final BlockQuote block = new BlockQuote();

    public BlockQuoteParser(BasedSequence marker) {
        block.setOpeningMarker(marker);
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(Block block) {
        return true;
    }

    @Override
    public BlockQuote getBlock() {
        return block;
    }
    
    @Override
    public void closeBlock(ParserState parserState) {
        block.setCharsFromContent();
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        int nextNonSpace = state.getNextNonSpaceIndex();
        if (isMarker(state, nextNonSpace)) {
            int newColumn = state.getColumn() + state.getIndent() + 1;
            // optional following space or tab
            if (Parsing.isSpaceOrTab(state.getLine(), nextNonSpace + 1)) {
                newColumn++;
            }
            return BlockContinue.atColumn(newColumn);
        } else {
            return BlockContinue.none();
        }
    }

    private static boolean isMarker(ParserState state, int index) {
        CharSequence line = state.getLine();
        return state.getIndent() < Parsing.CODE_BLOCK_INDENT && index < line.length() && line.charAt(index) == '>';
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }
    
    private static class BlockFactory extends AbstractBlockParserFactory {
        private BlockFactory(DataHolder options) {
            super(options);
        }

        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            if (isMarker(state, nextNonSpace)) {
                int newColumn = state.getColumn() + state.getIndent() + 1;
                // optional following space or tab
                if (Parsing.isSpaceOrTab(state.getLine(), nextNonSpace + 1)) {
                    newColumn++;
                }
                return BlockStart.of(new BlockQuoteParser(state.getLine().subSequence(nextNonSpace, nextNonSpace +1))).atColumn(newColumn);
            } else {
                return BlockStart.none();
            }
        }
    }
}
