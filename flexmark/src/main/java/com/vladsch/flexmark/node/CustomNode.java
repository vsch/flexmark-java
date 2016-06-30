package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

public abstract class CustomNode extends Node {
    public CustomNode() {
    }

    public CustomNode(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
