package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public abstract class Heading extends Block {
    protected int level;

    public Heading() {
    }

    public Heading(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public Heading(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public Heading(BlockContent blockContent) {
        super(blockContent);
    }

    public Heading(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
