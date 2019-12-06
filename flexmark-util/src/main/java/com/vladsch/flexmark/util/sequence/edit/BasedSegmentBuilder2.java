package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class BasedSegmentBuilder2 extends SegmentBuilder2 {
    final @NotNull BasedSequence myBase;
    final @NotNull SegmentOptimizer2 myOptimizer;

    protected BasedSegmentBuilder2(@NotNull BasedSequence base) {
        this(base, new CharRecoveryOptimizer2(PositionAnchor.CURRENT));
    }

    protected BasedSegmentBuilder2(@NotNull BasedSequence base, @NotNull SegmentOptimizer2 optimizer) {
        super();
        myBase = base;
        myOptimizer = optimizer;
    }

    protected BasedSegmentBuilder2(@NotNull BasedSequence base, int options) {
        this(base, new CharRecoveryOptimizer2(PositionAnchor.CURRENT), options);
    }

    protected BasedSegmentBuilder2(@NotNull BasedSequence base, @NotNull SegmentOptimizer2 optimizer, int options) {
        super(options);
        myBase = base;
        myOptimizer = optimizer;
    }

    @Override
    protected Object[] optimizeText(@NotNull Object[] parts) {
        return myOptimizer.apply(myBase, parts);
    }

    @Override
    protected Object[] handleOverlap(@NotNull Object[] parts) {
        // convert overlap to text from our base
        // range overlaps with last segment in the list
        Range lastSeg = (Range) parts[0];
        CharSequence text = (CharSequence) parts[1];
        Range range = (Range) parts[2];
        assert !lastSeg.isNull() && lastSeg.getEnd() > range.getStart();

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

        // append overlap to text
        if (text.length() == 0) {
            parts[1] = myBase.subSequence(overlap.getStart(), overlap.getEnd()).toString();

        } else {
            parts[1] = text.toString() + myBase.subSequence(overlap.getStart(), overlap.getEnd()).toString();
        }
        parts[2] = after;

        return parts;
    }

    public String toStringWithRangesVisibleWhitespace() {
        return super.toStringWithRangesVisibleWhitespace(myBase);
    }

    public String toStringWithRanges() {
        return super.toStringWithRanges(myBase);
    }

    public String toStringChars() {
        return super.toString(myBase);
    }

    @NotNull
    public static BasedSegmentBuilder2 emptyBuilder(@NotNull BasedSequence sequence) {
        return new BasedSegmentBuilder2(sequence);
    }

    @NotNull
    public static BasedSegmentBuilder2 emptyBuilder(@NotNull BasedSequence sequence, int options) {
        return new BasedSegmentBuilder2(sequence, options);
    }

    @NotNull
    public static BasedSegmentBuilder2 emptyBuilder(@NotNull BasedSequence sequence, @NotNull SegmentOptimizer2 optimizer) {
        return new BasedSegmentBuilder2(sequence, optimizer);
    }

    @NotNull
    public static BasedSegmentBuilder2 emptyBuilder(@NotNull BasedSequence sequence, @NotNull SegmentOptimizer2 optimizer, int options) {
        return new BasedSegmentBuilder2(sequence, optimizer, options);
    }
}
