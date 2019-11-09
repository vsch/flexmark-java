package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * A BasedSequence with offset tracking that follows editing operations and subSequence() chopping
 * <p>
 * a subSequence() returns a sub-sequence from the original base sequence, possibly with a prefix if it falls in range
 */
public final class TrackedBasedSequence extends BasedSequenceImpl implements ReplacedBasedSequence {
    private final @NotNull BasedSequence base;
    private final @NotNull BasedOffsetTracker offsetTracker;

    private TrackedBasedSequence(@NotNull BasedSequence baseSeq, @NotNull BasedOffsetTracker offsetTracker) {
        if (baseSeq instanceof TrackedBasedSequence) {
            // replace it as the tracker with this class
            this.base = ((TrackedBasedSequence) baseSeq).base;
        } else {
            this.base = baseSeq;
        }

        this.offsetTracker = offsetTracker;
    }

    @NotNull
    public BasedOffsetTracker getOffsetTracker() {
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
        BasedOffsetTracker modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, null);
        if (modifiedTracker != null) {
            return new TrackedBasedSequence(modifiedSeq, modifiedTracker);
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
    public <B extends SequenceBuilder<B, BasedSequence>> @NotNull B getBuilder() {
        //noinspection unchecked
        return (B) new BasedSequenceBuilder(getBaseSequence(), -1, offsetTracker);
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        BasedSequence modifiedSeq = base.subSequence(startIndex, endIndex);
        BasedOffsetTracker modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, null);
        if (modifiedTracker != null) {
            return new TrackedBasedSequence(modifiedSeq, modifiedTracker);
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
    static BasedSequence create(@NotNull BasedSequence base, @NotNull BasedOffsetTracker offsetTracker) {
        return new TrackedBasedSequence(base, offsetTracker);
    }

    @NotNull
    public static BasedSequence trackOffset(@NotNull BasedSequence baseSeq, int markerIndex, @NotNull TrackerDirection trackerDirection) {
        BasedOffsetTracker tracker = BasedOffsetTracker.create(baseSeq, markerIndex, trackerDirection);
        return tracker != null ? new TrackedBasedSequence(baseSeq, tracker) : baseSeq;
    }
}
