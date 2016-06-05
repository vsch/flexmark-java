package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;

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

    public void setCharsFromChildren() {
        Node firstChild = getFirstChild();
        Node lastChild = getLastChild();

        if (firstChild != null && lastChild != null && firstChild.getChars() != SubSequence.NULL && lastChild.getChars() != SubSequence.NULL) {
            int endOffset = lastChild.getEndOffset();
            int startOffset = firstChild.getStartOffset();
            setChars(firstChild.getChars().baseSubSequence(startOffset > endOffset ? endOffset : startOffset, endOffset));
        }
    }
}
