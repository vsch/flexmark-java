package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public abstract class CustomBlock extends Block {
    public CustomBlock() {
    }

    public CustomBlock(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public CustomBlock(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public CustomBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public CustomBlock(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
