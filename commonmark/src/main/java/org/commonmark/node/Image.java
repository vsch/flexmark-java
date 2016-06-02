package org.commonmark.node;

public class Image extends LinkNode {

    public Image() {
    }

    public Image(int offsetInParent, int textLength, int altOpenOffset, int altTextOffset, int altCloseOffset, int linkOpenOffset, int linkUrlOffset, int titleOpenOffset, int titleTextOffset, int titleCloseOffset, int linkCloseOffset) {
        super(offsetInParent, textLength,
                altOpenOffset - offsetInParent,
                altTextOffset - offsetInParent,
                altCloseOffset - offsetInParent,
                linkOpenOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                titleOpenOffset - offsetInParent,
                titleTextOffset - offsetInParent,
                titleCloseOffset - offsetInParent,
                linkCloseOffset - offsetInParent
        );
    }

    public Image(int offsetInParent, int textLength, int altOpenOffset, int altTextOffset, int altCloseOffset, int linkOpenOffset, int linkUrlOffset, int linkCloseOffset) {
        super(offsetInParent, textLength,
                altOpenOffset - offsetInParent,
                altTextOffset - offsetInParent,
                altCloseOffset - offsetInParent,
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
