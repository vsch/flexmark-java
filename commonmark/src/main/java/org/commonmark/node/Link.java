package org.commonmark.node;

public class Link extends LinkNode {
    public Link(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkOpenOffset, int linkUrlOffset, int titleOpenOffset, int titleTextOffset, int titleCloseOffset, int linkCloseOffset) {
        super(offsetInParent, textLength,
                textOpenOffset - offsetInParent,
                textOffset - offsetInParent,
                textCloseOffset - offsetInParent,
                linkOpenOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                titleOpenOffset - offsetInParent,
                titleTextOffset - offsetInParent,
                titleCloseOffset - offsetInParent,
                linkCloseOffset - offsetInParent
        );
    }

    public Link(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkOpenOffset, int linkUrlOffset, int linkCloseOffset) {
        super(offsetInParent, textLength,
                textOpenOffset - offsetInParent,
                textOffset - offsetInParent,
                textCloseOffset - offsetInParent,
                linkOpenOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                linkCloseOffset - offsetInParent, //titleOpenOffset
                linkCloseOffset - offsetInParent, //titleTextOffset
                linkCloseOffset - offsetInParent, //titleCloseOffset
                linkCloseOffset - offsetInParent
        );
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
