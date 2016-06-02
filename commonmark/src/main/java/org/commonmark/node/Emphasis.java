package org.commonmark.node;

public class Emphasis extends DelimitedNode {
    public Emphasis() {
    }

    public Emphasis(int offsetInParent, int textLength, int contentOffset, int closeDelimiterOffset) {
        super(offsetInParent, textLength, contentOffset, closeDelimiterOffset);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
