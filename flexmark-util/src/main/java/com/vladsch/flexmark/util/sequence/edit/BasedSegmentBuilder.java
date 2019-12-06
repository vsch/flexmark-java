package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class BasedSegmentBuilder extends SegmentBuilderBase<SegmentBuilderBase> implements IBasedSegmentBuilder<BasedSegmentBuilder> {
    private final @NotNull BasedSequence myBase;

    protected BasedSegmentBuilder(@NotNull BasedSequence base) {
        super();
        myBase = base;
    }

    protected BasedSegmentBuilder(@NotNull BasedSequence base, int options) {
        super(options);
        myBase = base;
    }

    public BasedSegmentBuilder(@NotNull BasedSegmentBuilder other) {
        super(other);
        myBase = other.myBase;
    }

    public String toStringWithRangesVisibleWhitespace() {
        return super.toStringWithRangesVisibleWhitespace(myBase);
    }

    public String toStringWithRanges() {
        return super.toStringWithRanges(myBase);
    }

    @Override
    public void handleOverlap(@NotNull Range range) {
        // convert overlap to text from our base
        // range overlaps with last segment in the list
        Seg lastSeg = lastSeg();
        assert !lastSeg.isNullRange() && lastSeg.getEnd() > range.getStart();

        Range overlap;
        Range after = Range.NULL;

        if (range.getEnd() <= lastSeg.getStart()) {
            // the whole thing is before
            overlap = range;
        } else if (range.getStart() <= lastSeg.getStart()) {
            // part before, maybe some after
            overlap = Range.of(range.getStart(), Math.min(range.getEnd(), lastSeg.getEnd()));
            if (lastSeg.getEnd() < range.getEnd()) {
                after = Range.of(lastSeg.getEnd(), range.getEnd());
            }
        } else if (range.getEnd() <= lastSeg.getEnd()) {
            // all contained within
            overlap = range;
        } else  {
            assert range.getStart() < lastSeg.getEnd();
            overlap = range.withEnd(lastSeg.getEnd());
            after = range.withStart(lastSeg.getEnd());
        }

        int overlapSpan = overlap.getSpan();
        assert overlapSpan + after.getSpan() == range.getSpan();

        // NOTE: addBaseSeg adds length
        myLength += overlapSpan;
        myTextLength += overlapSpan;

        if (lastSeg.isText()) {
            // can append to it
            setLastSeg(lastSeg.withTextSuffix(myBase.subSequence(overlap.getStart(), overlap.getEnd()).toString()));
        } else {
            // will append overlap text, followed by non-overlapping range if any
            myParts.add(Seg.textSeg(lastSeg.getEnd(), myBase.subSequence(overlap.getStart(), overlap.getEnd()).toString()));
        }

        if (after.isNotEmpty()) {
            // there is a part of the overlap outside the last seg range
            // append the chopped off base part
            addBaseSeg(Seg.baseSeg(after.getStart(), after.getEnd()));
        }
    }

    public String toStringChars() {
        return super.toString(myBase);
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence) {
        return new BasedSegmentBuilder(sequence);
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence, int options) {
        return new BasedSegmentBuilder(sequence, options);
    }
}
