package com.vladsch.flexmark.util.sequence.edit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SegmentSubSequence extends SegmentSubList {
    private final @Nullable EditOp myFirstPartial;  // first partial range/string
    private final @Nullable EditOp myLastPartial;   // last partial range/string

    public SegmentSubSequence(EditOp[] parts, int length, int startOffset, int endOffset, int startIndex, int endIndex, @Nullable EditOp firstPartial, @Nullable EditOp lastPartial) {
        super(parts, length, startOffset, endOffset, startIndex, endIndex);
        myFirstPartial = firstPartial;
        myLastPartial = lastPartial;
    }

    @Override
    protected boolean addFirstParts(@NotNull BasedSegmentBuilder builder) {
        boolean hadOutOfBase = super.addFirstParts(builder);
        if (myFirstPartial != null) {
            builder.append(myFirstPartial);
            hadOutOfBase = myFirstPartial.isTextOp();
        }

        return hadOutOfBase;
    }

    @Override
    protected boolean addLastParts(@NotNull BasedSegmentBuilder builder) {
        if (myFirstPartial != null) builder.append(myFirstPartial);
        return super.addLastParts(builder) || myLastPartial != null && myLastPartial.isTextOp();
    }

    @Nullable
    @Override
    public EditOp getFirstPartial() {
        return myFirstPartial;
    }

    @Nullable
    @Override
    public EditOp getLastPartial() {
        return myLastPartial;
    }
}
