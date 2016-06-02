package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

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
