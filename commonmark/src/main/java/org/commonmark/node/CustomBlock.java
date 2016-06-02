package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

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
