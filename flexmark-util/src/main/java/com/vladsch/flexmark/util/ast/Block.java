package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Block extends ContentNode {
    public Block() {
    }

    public Block(BasedSequence chars) {
        super(chars);
    }

    public Block(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public Block(List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public Block(BlockContent blockContent) {
        super(blockContent);
    }

    @Nullable
    public Block getParent() {
        return (Block) super.getParent();
    }

    @Override
    protected void setParent(@Nullable Node parent) {
        if (!(parent instanceof Block)) {
            throw new IllegalArgumentException("Parent of block must also be block (can not be inline)");
        }
        super.setParent(parent);
    }
}
