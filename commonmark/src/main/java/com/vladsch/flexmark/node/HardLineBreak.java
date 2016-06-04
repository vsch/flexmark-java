package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

public class HardLineBreak extends Node {
    public HardLineBreak() {
    }

    public HardLineBreak(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
