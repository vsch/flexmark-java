package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;

import java.util.List;

public class Document extends Block {
    public Document() {
    }

    public Document(BasedSequence chars) {
        super(chars);

    }

    public Document(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Document(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
