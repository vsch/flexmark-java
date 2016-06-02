package org.commonmark.node;

public class SoftLineBreak extends Node {
    public SoftLineBreak() {
    }

    public SoftLineBreak(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
