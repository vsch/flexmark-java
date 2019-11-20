package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class BasedSegmentBuilder extends SegmentBuilder {
    private final @NotNull BasedSequence myBase;

    protected BasedSegmentBuilder(@NotNull BasedSequence base) {
        super();
        myBase = base;
    }

    public BasedSegmentBuilder(@NotNull BasedSegmentBuilder other) {
        super(other);
        myBase = other.myBase;
    }

    @Override
    public void handleOverlap(@NotNull Range range) {
        // convert overlap to text from our base
        // range overlaps with last segment in the list
        Seg lastSeg = lastSeg();
        assert !lastSeg.isNullRange() && lastSeg.getEnd() > range.getStart();

        int length = lastSeg.getEnd() - range.getStart();
        myLength += length;
        myTextLength += length;

        if (lastSeg.isText()) {
            // can append to it
            setLastSeg(lastSeg.withTextSuffix(myBase.subSequence(range.getStart(), lastSeg.getEnd()).toString()));
        } else {
            // will append overlap text, followed by non-overlapping range if any
            myParts.add(Seg.textSeg(lastSeg.getEnd(), myBase.subSequence(range.getStart(), lastSeg.getEnd()).toString()));
        }

        if (lastSeg.getEnd() < range.getEnd()) {
            // there is a part of the overlap outside the last seg range
            // append the chopped off base part
            addBaseSeg(Seg.baseSeg(lastSeg.getEnd(), range.getEnd()));
        }
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence) {
        return new BasedSegmentBuilder(sequence);
    }
}
