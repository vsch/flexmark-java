package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * A BasedSequence with offset tracking that follows editing operations and subSequence() chopping
 * <p>
 * a subSequence() returns a sub-sequence from the original base sequence, possibly with a prefix if it falls in range
 */
public final class BasedTrackedSequence extends BasedSequenceImpl implements ReplacedBasedSequence {
    private final @NotNull BasedSequence base;
    private final @NotNull OffsetTracker offsetTracker;

    private BasedTrackedSequence(@NotNull BasedSequence baseSeq, @NotNull OffsetTracker offsetTracker) {
        if (baseSeq instanceof BasedTrackedSequence) {
            // replace it as the tracker with this class
            this.base = ((BasedTrackedSequence) baseSeq).base;
        } else {
            this.base = baseSeq;
        }

        this.offsetTracker = offsetTracker;
    }

    @NotNull
    public OffsetTracker getOffsetTracker() {
        return offsetTracker;
    }

    @NotNull
    @Override
    public Object getBase() {
        return base.getBase();
    }

    @NotNull
    @Override
    public BasedSequence getBaseSequence() {
        return base.getBaseSequence();
    }

    @Override
    public int getStartOffset() {
        return base.getStartOffset();
    }

    @Override
    public int getEndOffset() {
        return base.getEndOffset();
    }

    @NotNull
    @Override
    public Range getSourceRange() {
        return base.getSourceRange();
    }

    @NotNull
    @Override
    public BasedSequence baseSubSequence(int startIndex, int endIndex) {
        BasedSequence modifiedSeq = base.baseSubSequence(startIndex, endIndex);
        OffsetTracker modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, null);
        if (modifiedTracker != null) {
            return new BasedTrackedSequence(modifiedSeq, modifiedTracker);
        }
        return modifiedSeq;
    }

    @Override
    public int length() {
        return base.length();
    }

    @Override
    public int getIndexOffset(int index) {
        return base.getIndexOffset(index);
    }

    @Override
    public char charAt(int index) {
        return base.charAt(index);
    }

    @Override
    public int getTrackedIndex() {
        return offsetTracker.getTrackedIndex(this);
    }

    @Override
    public int getTrackedOffset(int startOffset, int maxOffset) {
        int index = offsetTracker.getTrackedIndex(this);
        return index < 0 ? -1 : Math.min(maxOffset, Math.max(0, index + startOffset));
    }

    @Override
    public <B extends SequenceBuilder<B, BasedSequence>> @NotNull B getBuilder() {
        //noinspection unchecked
        return (B) new BasedSequenceBuilder(getBaseSequence(), -1, offsetTracker);
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        BasedSequence modifiedSeq = base.subSequence(startIndex, endIndex);
        OffsetTracker modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, null);
        if (modifiedTracker != null) {
            return new BasedTrackedSequence(modifiedSeq, modifiedTracker);
        }
        return modifiedSeq;
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        base.appendTo(sb);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @NotNull
    static BasedSequence create(@NotNull BasedSequence base, @NotNull OffsetTracker offsetTracker) {
        return new BasedTrackedSequence(base, offsetTracker);
    }

    @NotNull
    public static BasedSequence trackOffset(@NotNull BasedSequence baseSeq, int markerIndex, @NotNull TrackerDirection trackerDirection) {
        OffsetTracker tracker = OffsetTracker.create(baseSeq, markerIndex, trackerDirection);
        return tracker != null ? new BasedTrackedSequence(baseSeq, tracker) : baseSeq;
    }
}
