package org.commonmark.node;

public class StrongEmphasis extends DelimitedNode {
    public StrongEmphasis() {
    }

    public StrongEmphasis(int offsetInParent, int textLength, int contentOffset, int closeDelimiterOffset) {
        super(offsetInParent, textLength, contentOffset, closeDelimiterOffset);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
