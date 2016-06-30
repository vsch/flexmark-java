package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.Parsing;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.IndentedCodeBlock;
import com.vladsch.flexmark.node.Paragraph;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Pattern;

public class IndentedCodeBlockParser extends AbstractBlockParser {

    private static final Pattern TRAILING_BLANK_LINES = Pattern.compile("(?:\n[ \t]*)+$");

    private final IndentedCodeBlock block = new IndentedCodeBlock();
    private BlockContent content = new BlockContent();

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.getIndent() >= Parsing.CODE_BLOCK_INDENT) {
            return BlockContinue.atColumn(state.getColumn() + Parsing.CODE_BLOCK_INDENT);
        } else if (state.isBlank()) {
            return BlockContinue.atIndex(state.getNextNonSpaceIndex());
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
        //String literal = TRAILING_BLANK_LINES.matcher(contentString).replaceFirst("\n");
        block.setContent(content);
        content = null;
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

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            // An indented code block cannot interrupt a paragraph.
            if (state.getIndent() >= Parsing.CODE_BLOCK_INDENT && !state.isBlank() && !(state.getActiveBlockParser().getBlock() instanceof Paragraph)) {
                return BlockStart.of(new IndentedCodeBlockParser()).atColumn(state.getColumn() + Parsing.CODE_BLOCK_INDENT);
            } else {
                return BlockStart.none();
            }
        }
    }
}

