package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class Paragraph extends Block {
    public Paragraph() {
    }

    public Paragraph(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public Paragraph(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public Paragraph(BlockContent blockContent) {
        super(blockContent);
    }

    public Paragraph(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
