package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class ParagraphParser extends AbstractBlockParser {

    private final Paragraph block = new Paragraph();
    private BlockContent content = new BlockContent();

    public BlockContent getBlockContent() {
        return content;
    }

    @Override
    public Paragraph getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (!state.isBlank()) {
            return BlockContinue.atIndex(state.getIndex());
        } else {
            block.setTrailingBlankLine(true);
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public boolean isParagraphParser() {
        return true;
    }

    @Override
    public boolean isInterruptible() {
        return true;
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setContent(content);
        content = null;
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        inlineParser.parse(getBlock().getContentChars(), getBlock());
    }

    public static class Factory implements BlockParserFactory {
        @Override
        public BlockStart tryStart(final ParserState state, final MatchedBlockParser matchedBlockParser) {
            return null;
        }
    }
}
