package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

public abstract class CustomNode<V> extends Node {
    public CustomNode() {
    }

    public CustomNode(BasedSequence chars) {
        super(chars);
    }
    
    public abstract void accept(V visitor);

    @Override
    final public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
