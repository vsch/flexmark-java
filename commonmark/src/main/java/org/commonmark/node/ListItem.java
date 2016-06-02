package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class ListItem extends Block {
    public ListItem() {
    }

    public ListItem(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public ListItem(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public ListItem(BlockContent blockContent) {
        super(blockContent);
    }

    public ListItem(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
