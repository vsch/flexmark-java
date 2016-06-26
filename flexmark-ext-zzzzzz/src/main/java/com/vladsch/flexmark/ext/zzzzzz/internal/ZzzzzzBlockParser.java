package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Pattern;

public class ZzzzzzBlockParser extends AbstractBlockParser {
    private static String COL = "\\s*:?-{3,}:?\\s*";
    private static Pattern ZZZZZZ_BLOCK_START = Pattern.compile("");
    private static Pattern ZZZZZZ_BLOCK_CONTINUE = Pattern.compile("");

    private final ZzzzzzBlock block = new ZzzzzzBlock();
    private BlockContent content = new BlockContent();
    private final ZzzzzzOptions options;

    private ZzzzzzBlockParser(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (ZZZZZZ_BLOCK_CONTINUE.matcher(state.getLine()).matches()) {
            return BlockContinue.atIndex(state.getIndex());
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setContent(content);
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
        private final ZzzzzzOptions options;

        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new ZzzzzzOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            if (ZZZZZZ_BLOCK_START.matcher(line).matches()) {
                return BlockStart.of(new ZzzzzzBlockParser(state.getProperties()))
                        .atIndex(state.getIndex())
                        //.replaceActiveBlockParser()
                        ;
            }
            return BlockStart.none();
        }
    }
}
