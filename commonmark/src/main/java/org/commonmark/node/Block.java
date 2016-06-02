package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public abstract class Block extends SegmentedNode {
    public Block() {
    }

    public Block(BasedSequence chars) {
        super(chars);
    }

    public Block(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Block(BlockContent blockContent) {
        super(blockContent);
    }

    public Block getParent() {
        return (Block) super.getParent();
    }

    @Override
    protected void setParent(Node parent) {
        if (!(parent instanceof Block)) {
            throw new IllegalArgumentException("Parent of block must also be block (can not be inline)");
        }
        super.setParent(parent);
    }
}
