package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

public class SoftLineBreak extends Node {
    public SoftLineBreak() {
    }

    public SoftLineBreak(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
