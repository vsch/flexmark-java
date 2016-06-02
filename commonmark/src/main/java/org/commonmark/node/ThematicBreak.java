package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class ThematicBreak extends Block {
    public ThematicBreak() {
    }

    public ThematicBreak(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public ThematicBreak(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public ThematicBreak(BlockContent blockContent) {
        super(blockContent);
    }

    public ThematicBreak(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
