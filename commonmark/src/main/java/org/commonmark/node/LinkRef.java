package org.commonmark.node;

public class LinkRef extends RefNode {

    public LinkRef() {
    }

    public LinkRef(int offsetInParent, int textLength, int refOpenOffset, int refTextOffset, int refCloseOffset, int refIdOpenOffset, int refIdOffset, int refIdCloseOffset) {
        super(offsetInParent, textLength, refOpenOffset, refTextOffset, refCloseOffset, refIdOpenOffset, refIdOffset, refIdCloseOffset);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
