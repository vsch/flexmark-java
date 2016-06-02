package org.commonmark.node;

public abstract class DelimitedNode extends SegmentedNode {
    public static int OPEN_MARKER_SEGMENT = 0;
    public static int CONTENT_SEGMENT = 1;
    public static int CLOSE_MARKER_SEGMENT = 2;

    public DelimitedNode() {
    }

    public DelimitedNode(int offsetInParent, int textLength, int contentOffset, int closeDelimiterOffset) {
        super(offsetInParent, textLength, contentOffset, closeDelimiterOffset);
    }

    public CharSequence getOpeningMarkerChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, OPEN_MARKER_SEGMENT);
    }

    public CharSequence getContentChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, CONTENT_SEGMENT);
    }

    public CharSequence getClosingMarkerChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, CLOSE_MARKER_SEGMENT);
    }

    public CharSequence getOpeningMarkerChars() {
        return getSegmentChars(getCharSequence(), OPEN_MARKER_SEGMENT);
    }

    public CharSequence getContentChars() {
        return getSegmentChars(getCharSequence(), CONTENT_SEGMENT);
    }

    public CharSequence getClosingMarkerChars() {
        return getSegmentChars(getCharSequence(), CLOSE_MARKER_SEGMENT);
    }

}
