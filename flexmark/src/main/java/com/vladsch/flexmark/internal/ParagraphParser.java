package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Paragraph;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.ParserState;

public class ParagraphParser extends AbstractBlockParser {

    private final Paragraph block = new Paragraph();
    private BlockContent content = new BlockContent();

    public BlockContent getContent() {
        return content;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (!state.isBlank()) {
            return BlockContinue.atIndex(state.getIndex());
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(BasedSequence line, int eolLength) {
        content.add(line, eolLength);
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setContent(content);
        BasedSequence oldContentChars = block.getChars();

        parserState.preProcessBlock(block, parserState);

        if (block.getChars().isBlank()) {
            block.unlink();
            content = null;
        } else {
            BasedSequence contentChars = block.getChars();
            if (oldContentChars.getStartOffset() != contentChars.getStartOffset() || oldContentChars.getEndOffset() != contentChars.getEndOffset()) {
                // skip lines that contained references
                int iMax = content.getLineCount();
                int i = 0;

                for (i = 0; i < iMax; i++) {
                    if (content.getLine(i).getStartOffset() >= contentChars.getStartOffset()) break;
                }

                content = new BlockContent(content, i, iMax);
                block.setContent(content);
            }
        }
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        if (content != null) {
            inlineParser.parse(content.getContents(), block);
        }
    }

}
