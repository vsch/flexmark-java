package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;

import java.util.List;

public abstract class CustomBlock extends Block {
    public CustomBlock() {
    }

    public CustomBlock(BasedSequence chars) {
        super(chars);
    }

    public CustomBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public CustomBlock(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
