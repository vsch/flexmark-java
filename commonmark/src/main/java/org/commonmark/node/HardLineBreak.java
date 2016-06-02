package org.commonmark.node;

public class HardLineBreak extends Node {
    public HardLineBreak() {
    }

    public HardLineBreak(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
