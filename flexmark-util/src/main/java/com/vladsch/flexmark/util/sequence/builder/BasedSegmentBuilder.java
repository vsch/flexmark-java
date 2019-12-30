package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class BasedSegmentBuilder extends SegmentBuilderBase<BasedSegmentBuilder> implements IBasedSegmentBuilder<BasedSegmentBuilder> {
    final @NotNull BasedSequence baseSeq;
    final @NotNull SegmentOptimizer optimizer;

    protected BasedSegmentBuilder(@NotNull BasedSequence baseSeq) {
        this(baseSeq, new CharRecoveryOptimizer(PositionAnchor.CURRENT));
    }

    protected BasedSegmentBuilder(@NotNull BasedSequence baseSeq, @NotNull SegmentOptimizer optimizer) {
        super();
        this.baseSeq = baseSeq.getBaseSequence();
        this.optimizer = optimizer;
    }

    protected BasedSegmentBuilder(@NotNull BasedSequence baseSeq, int options) {
        this(baseSeq, new CharRecoveryOptimizer(PositionAnchor.CURRENT), options);
    }

    protected BasedSegmentBuilder(@NotNull BasedSequence baseSeq, @NotNull SegmentOptimizer optimizer, int options) {
        super(options);
        this.baseSeq = baseSeq.getBaseSequence();
        this.optimizer = optimizer;
    }

    @Override
    public @NotNull BasedSequence getBaseSequence() {
        return baseSeq;
    }

    @Override
    protected Object[] optimizeText(@NotNull Object[] parts) {
        return optimizer.apply(baseSeq, parts);
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
        } else {
            assert range.getStart() < lastSeg.getEnd();
            overlap = range.withEnd(lastSeg.getEnd());
            after = range.withStart(lastSeg.getEnd());
        }

        int overlapSpan = overlap.getSpan();
        assert overlapSpan + after.getSpan() == range.getSpan();

        // append overlap to text
        if (text.length() == 0) {
            parts[1] = baseSeq.subSequence(overlap.getStart(), overlap.getEnd()).toString();
        } else {
            parts[1] = text.toString() + baseSeq.subSequence(overlap.getStart(), overlap.getEnd()).toString();
        }
        parts[2] = after;

        return parts;
    }

    @NotNull
    @Override
    public String toStringWithRangesVisibleWhitespace() {
        return super.toStringWithRangesVisibleWhitespace(baseSeq);
    }

    @NotNull
    @Override
    public String toStringWithRanges() {
        return super.toStringWithRanges(baseSeq);
    }

    @NotNull
    @Override
    public String toStringChars() {
        return super.toString(baseSeq);
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence) {
        return new BasedSegmentBuilder(sequence);
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence, int options) {
        return new BasedSegmentBuilder(sequence, options);
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence, @NotNull SegmentOptimizer optimizer) {
        return new BasedSegmentBuilder(sequence, optimizer);
    }

    @NotNull
    public static BasedSegmentBuilder emptyBuilder(@NotNull BasedSequence sequence, @NotNull SegmentOptimizer optimizer, int options) {
        return new BasedSegmentBuilder(sequence, optimizer, options);
    }
}
