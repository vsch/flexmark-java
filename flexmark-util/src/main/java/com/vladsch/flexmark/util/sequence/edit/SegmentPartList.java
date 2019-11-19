package com.vladsch.flexmark.util.sequence.edit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Full Segment part list, start index 0, end index parts.length
 */
public class SegmentPartList implements SegmentHolder {
    protected final EditOp[] myParts;   // all parts from parent
    protected final int myLength;       //length of all segments
    protected final int myStartOffset;    // base start offset of sequence
    protected final int myEndOffset;      // base end offset of sequence

    public SegmentPartList(EditOp[] parts, int length, int startOffset, int endOffset) {
        myParts = parts;
        myLength = length;
        myStartOffset = startOffset;
        myEndOffset = endOffset;
    }

    @NotNull
    @Override
    final public EditOp[] getParts() {
        return myParts;
    }

    @Override
    final public int getLength() {
        return myLength;
    }

    @Override
    final public int getEndOffset() {
        return myEndOffset;
    }

    @Override
    final public int getStartOffset() {
        return myStartOffset;
    }

    public int getStartIndex() {
        return 0;
    }

    public int getEndIndex() {
        return myParts.length;
    }

    @Nullable
    @Override
    public EditOp getFirstPartial() {
        return null;
    }

    @Override
    @Nullable
    public EditOp getLastPartial() {
        return null;
    }

    protected boolean addFirstParts(@NotNull BasedSegmentBuilder builder) {
        builder.append(myStartOffset, myStartOffset);
        return false;
    }

    protected boolean addLastParts(@NotNull BasedSegmentBuilder builder) {
        builder.append(myEndOffset, myEndOffset);
        return false;
    }

    @Override
    final public @NotNull SegmentHolder getSubSequence(int startIndex, int endIndex) {
        // FIX: implement
        return null;
    }

    /**
     * Add segments for this sequence, replacing out of base characters with strings
     *
     * @param builder builder
     * @return true if had out of base chars
     */
    final public boolean addSegments(@NotNull BasedSegmentBuilder builder) {
        boolean noOutOfBase = addLastParts(builder);

        int iMax = getEndIndex();
        for (int i = getStartIndex(); i < iMax; i++) {
            builder.append(myParts[i]);
            if (noOutOfBase && myParts[i].isTextOp()) noOutOfBase = false;
        }

        return addLastParts(builder) || noOutOfBase;
    }
}
