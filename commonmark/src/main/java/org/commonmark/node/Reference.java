package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class Reference extends Block {
    public static int OPEN_MARKER_SEGMENT = 0;
    public static int REF_ID_SEGMENT = 1;
    public static int CLOSE_MARKER_SEGMENT = 2;
    public static int URL_SEGMENT = 3;
    public static int TITLE_OPEN_MARKER_SEGMENT = 4;
    public static int TITLE_TEXT_SEGMENT = 5;
    public static int TITLE_CLOSE_MARKER_SEGMENT = 6;

    public Reference() {
    }

    public Reference(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public Reference(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkUrlOffset, int titleOpenOffset, int titleTextOffset, int titleCloseOffset) {
        super(offsetInParent, textLength,
                textOpenOffset - offsetInParent,
                textOffset - offsetInParent,
                textCloseOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                titleOpenOffset - offsetInParent,
                titleTextOffset - offsetInParent,
                titleCloseOffset - offsetInParent
        );
    }

    public Reference(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkUrlOffset) {
        super(offsetInParent, textLength,
                textOpenOffset - offsetInParent,
                textOffset - offsetInParent,
                textCloseOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                textLength, //titleOpenOffset
                textLength, //titleTextOffset
                textLength, //titleCloseOffset
                textLength
        );
    }

    public void setSourcePos(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkUrlOffset, int titleOpenOffset, int titleTextOffset, int titleCloseOffset) {
        setSourcePos(offsetInParent, textLength);
        setSegmentOffsets(new int[] {
                textOpenOffset - offsetInParent,
                textOffset - offsetInParent,
                textCloseOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                titleOpenOffset - offsetInParent,
                titleTextOffset - offsetInParent,
                titleCloseOffset - offsetInParent
                }
        );
    }

    public void setSourcePos(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkUrlOffset) {
        setSourcePos(offsetInParent, textLength);
        setSegmentOffsets(new int[] {
                textOpenOffset - offsetInParent,
                textOffset - offsetInParent,
                textCloseOffset - offsetInParent,
                linkUrlOffset - offsetInParent,
                textLength, //titleOpenOffset
                textLength, //titleTextOffset
                textLength, //titleCloseOffset
                textLength
                }
        );
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public CharSequence getRefIdChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, REF_ID_SEGMENT);
    }

    public CharSequence getUrlChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, URL_SEGMENT);
    }

    @Override
    protected String toStringAttributes() {
        CharSequence charSequence = getCharSequence();
        return "refId=" + getRefIdChars(charSequence) + ", url=" + getUrlChars(charSequence);
    }

}
