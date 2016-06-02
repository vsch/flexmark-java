package org.commonmark.node;

public class Code extends DelimitedNode {
    public Code() {
    }

    public Code(int offsetInParent, int textLength, int contentOffset, int closeDelimiterOffset) {
        super(offsetInParent, textLength, contentOffset, closeDelimiterOffset);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
