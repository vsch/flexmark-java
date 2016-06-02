package org.commonmark.internal;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.Parsing;
import org.commonmark.node.Block;
import org.commonmark.node.Paragraph;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.ParserState;

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
    public void addLine(BasedSequence line) {
        content.add(line);
    }

    @Override
    public void closeBlock() {
    }

    public void closeBlock(InlineParserImpl inlineParser) {
        BasedSequence contentChars = content.getContents();
        boolean hasReferenceDefs = false;

        int pos;

        // try parsing the beginning as link reference definitions:
        while (contentChars.length() > 3 && contentChars.charAt(0) == '[') {
            pos = inlineParser.parseReference(contentChars);
            if (pos == 0) break;

            contentChars = contentChars.subSequence(pos, contentChars.length());
            hasReferenceDefs = true;
        }

        if (hasReferenceDefs && Parsing.isBlank(contentChars)) {
            block.unlink();
            content = null;
        } else {
            // skip lines that contained references
            int iMax = content.getLineCount();
            int i;

            for (i = 0; i < iMax; i++) {
                if (content.getLine(i).getStartOffset() >= contentChars.getStartOffset()) break;
            }

            content = new BlockContent(content, i, iMax);
        }
    }
}
