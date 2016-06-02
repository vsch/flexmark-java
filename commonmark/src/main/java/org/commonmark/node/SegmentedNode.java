package org.commonmark.node;

public abstract class SegmentedNode extends Node implements Segmented {
    protected int[] segmentOffsets;

    public SegmentedNode() {

    }

    public SegmentedNode(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength);
        this.segmentOffsets = segmentOffsets;
    }

    @Override
    public CharSequence getSegmentChars(CharSequence charSequence, int segmentIndex) {
        int offsetInParent = getOffsetInParent();

        if (segmentIndex == 0) {
            return charSequence.subSequence(offsetInParent, offsetInParent + segmentOffsets[segmentIndex]);
        } else if (segmentIndex >= segmentOffsets.length) {
            return charSequence.subSequence(offsetInParent + segmentOffsets[segmentIndex - 1], offsetInParent + textLength);
        } else {
            return charSequence.subSequence(offsetInParent + segmentOffsets[segmentIndex - 1], offsetInParent + segmentOffsets[segmentIndex]);
        }
    }

    @Override
    public int[] getSegmentOffsets() {
        return segmentOffsets;
    }

    public void setSegmentOffsets(int[] segmentOffsets) {
        this.segmentOffsets = segmentOffsets;
    }

    @Override
    public int getSegmentStartOffset(int segmentIndex) {
        int offsetInParent = getOffsetInParent();
        if (segmentIndex == 0) {
            return offsetInParent;
        } else {
            return offsetInParent + segmentOffsets[segmentIndex - 1];
        }
    }

    @Override
    public int getSegmentEndOffset(int segmentIndex) {
        int offsetInParent = getOffsetInParent();
        if (segmentIndex == 0) {
            return offsetInParent + segmentOffsets[segmentIndex];
        } else if (segmentIndex >= segmentOffsets.length) {
            return offsetInParent + textLength;
        } else {
            return offsetInParent + segmentOffsets[segmentIndex - 1];
        }
    }
}
