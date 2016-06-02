package org.commonmark.node;

public abstract class LinkNode extends SegmentedNode {
    public static int TEXT_OPEN_MARKER_SEGMENT = 0;
    public static int TEXT_SEGMENT = 1;
    public static int TEXT_CLOSE_MARKER_SEGMENT = 2;
    public static int LINK_OPEN_MARKER_SEGMENT = 3;
    public static int LINK_URL_SEGMENT = 4;
    public static int TITLE_OPEN_MARKER_SEGMENT = 5;
    public static int TITLE_TEXT_SEGMENT = 6;
    public static int TITLE_CLOSE_MARKER_SEGMENT = 7;
    public static int LINK_CLOSE_MARKER_SEGMENT = 8;

    public LinkNode() {
    }

    public LinkNode(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkOpenOffset, int linkUrlOffset, int titleOpenOffset, int titleTextOffset, int titleCloseOffset, int linkCloseOffset) {
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

    public LinkNode(int offsetInParent, int textLength, int textOpenOffset, int textOffset, int textCloseOffset, int linkOpenOffset, int linkUrlOffset, int linkCloseOffset) {
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

    public void setSourcePos(int offsetInParent, int textLength, int altOpenOffset, int altTextOffset, int altCloseOffset, int linkOpenOffset, int linkUrlOffset, int titleOpenOffset, int titleTextOffset, int titleCloseOffset, int linkCloseOffset) {
        setSourcePos(offsetInParent, textLength);
        setSegmentOffsets(new int[] {
                        altOpenOffset - offsetInParent,
                        altTextOffset - offsetInParent,
                        altCloseOffset - offsetInParent,
                        linkOpenOffset - offsetInParent,
                        linkUrlOffset - offsetInParent,
                        titleOpenOffset - offsetInParent,
                        titleTextOffset - offsetInParent,
                        titleCloseOffset - offsetInParent,
                        linkCloseOffset - offsetInParent
                }
        );
    }

    public void setSourcePos(int offsetInParent, int textLength, int altOpenOffset, int altTextOffset, int altCloseOffset, int linkOpenOffset, int linkUrlOffset, int linkCloseOffset) {
        setSourcePos(offsetInParent, textLength);
        setSegmentOffsets(new int[] {
                        altOpenOffset - offsetInParent,
                        altTextOffset - offsetInParent,
                        altCloseOffset - offsetInParent,
                        linkOpenOffset - offsetInParent,
                        linkUrlOffset - offsetInParent,
                        linkCloseOffset - offsetInParent, //titleOpenOffset
                        linkCloseOffset - offsetInParent, //titleTextOffset
                        linkCloseOffset - offsetInParent, //titleCloseOffset
                        linkCloseOffset - offsetInParent
                }
        );
    }

    public CharSequence getUrlChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, LINK_URL_SEGMENT);
    }

    public CharSequence getTextChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, TEXT_SEGMENT);
    }

    public CharSequence getTitleChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, TITLE_TEXT_SEGMENT);
    }

    @Override
    protected String toStringAttributes() {
        CharSequence charSequence = getCharSequence();
        return "text=" + getText(charSequence) + ", url=" + getUrlChars(charSequence) + ", title=" + getTitleChars(charSequence);
    }
}
