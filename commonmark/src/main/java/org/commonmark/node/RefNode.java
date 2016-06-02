package org.commonmark.node;

public abstract class RefNode extends SegmentedNode {
    public static int REF_OPEN_MARKER_SEGMENT = 0;
    public static int REF_TEXT_SEGMENT = 1;
    public static int REF_CLOSE_MARKER_SEGMENT = 2;
    public static int REF_ID_OPEN_MARKER_SEGMENT = 3;
    public static int REF_ID_SEGMENT = 4;
    public static int REF_ID_CLOSE_MARKER_SEGMENT = 5;

    public RefNode() {
    }

    public RefNode(int offsetInParent, int textLength, int refOpenOffset, int refTextOffset, int refCloseOffset, int refIdOpenOffset, int refIdOffset, int refIdCloseOffset) {
        super(offsetInParent, textLength,
                refOpenOffset - offsetInParent,
                refTextOffset - offsetInParent,
                refCloseOffset - offsetInParent,
                refIdOpenOffset - offsetInParent,
                refIdOffset - offsetInParent,
                refIdCloseOffset - offsetInParent
        );
    }

    public void  setSourcePos(int offsetInParent, int textLength, int refOpenOffset, int refTextOffset, int refCloseOffset, int refIdOpenOffset, int refIdOffset, int refIdCloseOffset) {
        setSourcePos(offsetInParent, textLength);
        setSegmentOffsets(new int[] {
                        refOpenOffset - offsetInParent,
                        refTextOffset - offsetInParent,
                        refCloseOffset - offsetInParent,
                        refIdOpenOffset - offsetInParent,
                        refIdOffset - offsetInParent,
                        refIdCloseOffset - offsetInParent
                }
        );
    }

    public boolean isRefIdOnly() {
        return segmentOffsets.length < REF_ID_OPEN_MARKER_SEGMENT;
    }

    public boolean isDummyRef() {
        return segmentOffsets.length > REF_ID_SEGMENT && getSegmentStartOffset(REF_ID_SEGMENT) == getSegmentEndOffset(REF_ID_SEGMENT);
    }

    public CharSequence getRefTextChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, REF_TEXT_SEGMENT);
    }

    public CharSequence getRefIdChars(CharSequence charSequence) {
        return getSegmentChars(charSequence, REF_ID_SEGMENT);
    }

    @Override
    protected String toStringAttributes() {
        CharSequence charSequence = getCharSequence();
        return "refText=" + getRefTextChars(charSequence) + ", refId=" + getRefIdChars(charSequence);
    }
}
