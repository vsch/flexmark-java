package com.vladsch.flexmark.util.sequence.edit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Segment sublist representing full parts only
 */
public class SegmentSubList extends SegmentPartList {
    private final int myStartIndex;     // start offset into parts
    private final int myEndIndex;       // end offset into parts

    public SegmentSubList(EditOp[] parts, int length, int startOffset, int endOffset, int startIndex, int endIndex) {
        super(parts, length,startOffset, endOffset);
        myStartIndex = startIndex;
        myEndIndex = endIndex;
    }

    public int getStartIndex() {
        return myStartIndex;
    }

    public int getEndIndex() {
        return myEndIndex;
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
}
