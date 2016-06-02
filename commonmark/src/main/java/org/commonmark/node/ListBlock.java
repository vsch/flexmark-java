package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public abstract class ListBlock extends Block {

    private boolean tight;

    public ListBlock() {
    }

    public ListBlock(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public ListBlock(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public ListBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public ListBlock(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    public boolean isTight() {
        return tight;
    }

    public void setTight(boolean tight) {
        this.tight = tight;
    }
}
