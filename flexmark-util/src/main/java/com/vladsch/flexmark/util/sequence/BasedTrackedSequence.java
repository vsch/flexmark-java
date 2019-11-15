package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.edit.SequenceBuilder;
import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import org.jetbrains.annotations.NotNull;

/**
 * A BasedSequence with offset tracking that follows editing operations and subSequence() chopping as best as it can
 * <p>
 * a subSequence() returns a sub-sequence from the original base sequence with updated offset tracking
 */
public final class BasedTrackedSequence extends BasedSequenceImpl implements ReplacedBasedSequence {
    private final @NotNull BasedSequence base;
    private final @NotNull OffsetTracker[] offsetTrackers;

    private BasedTrackedSequence(@NotNull BasedSequence baseSeq, @NotNull OffsetTracker[] offsetTrackers) {
        super(baseSeq instanceof BasedTrackedSequence ? ((BasedTrackedSequence) baseSeq).base.hashCode() : baseSeq.hashCode());

        if (baseSeq instanceof BasedTrackedSequence) {
            // replace it as the tracker with this class
            this.base = ((BasedTrackedSequence) baseSeq).base;
        } else {
            this.base = baseSeq;
        }

        this.offsetTrackers = offsetTrackers;
    }

    @NotNull
    public OffsetTracker[] getOffsetTrackers() {
        return offsetTrackers;
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
        // FIX: for editing
        return -1;
//        return offsetTrackers.getTrackedIndex(this);
    }

    @Override
    public int getTrackedOffset(int startOffset, int maxOffset) {
//        int index = offsetTrackers.getTrackedIndex(this);
//        return index < 0 ? -1 : Math.min(maxOffset, Math.max(0, index + startOffset));
        return -1;
    }

    @Override
    public <B extends SequenceBuilder<B, BasedSequence>> @NotNull B getBuilder() {
        //noinspection unchecked
        // FIX: for editing
//        return (B) new BasedSequenceBuilder(getBaseSequence(), offsetTrackers);
        return super.getBuilder();
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        BasedSequence modifiedSeq = base.subSequence(startIndex, endIndex);
//        OffsetTracker modifiedTracker = offsetTrackers.modifiedTracker(modifiedSeq, null);
//        if (modifiedTracker != null) {
//            return new BasedTrackedSequence(modifiedSeq, modifiedTracker);
//        }
        return modifiedSeq;
    }

    @NotNull
    static BasedSequence create(@NotNull BasedSequence baseSeq, @NotNull OffsetTracker offsetTracker) {
        // FIX: for editing
//        return new BasedTrackedSequence(base, offsetTracker);
        return baseSeq;
    }

    @NotNull
    public static BasedSequence trackOffset(@NotNull BasedSequence baseSeq, int markerIndex, @NotNull PositionAnchor positionAnchor) {
        // FIX: for editing
//        OffsetTracker tracker = OffsetTracker.create(baseSeq, markerIndex, trackerDirection);
//        return tracker != null ? new BasedTrackedSequence(baseSeq, tracker) : baseSeq;
        return baseSeq;
    }
}
