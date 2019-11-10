package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A position tracker within a based sequence
 * <p>
 */
public final class OffsetTracker implements Comparable<OffsetTracker> {
    // FIX: gather information as to location within the sequence so the sequence does not need to be stored, only its signature information
    private final BasedSequence baseSeq;
    private final int markerOffset;  // offset in original base, used only when a modified sequence contains this offset
    private final TrackerDirection myTrackerDirection;
    private final int markerIndex;   // index in actual sequence

    private OffsetTracker(BasedSequence baseSeq, int markerOffset, TrackerDirection trackerDirection) {
        this.baseSeq = baseSeq;
        this.markerOffset = markerOffset;
        this.myTrackerDirection = trackerDirection;
        this.markerIndex = markerOffset - baseSeq.getStartOffset();

        if (this.markerIndex < 0 || this.markerIndex > baseSeq.length()) {
            throw new IllegalArgumentException("Marker offset: " + markerOffset + " out of sequence: [" + baseSeq.getStartOffset() + ", " + baseSeq.getEndOffset() + "]");
        }

        // FIX: need to study the location around the marker offset in the base sequence such as
        //    left and right non-blank anchors, presence of EOL between marker and anchor (lowers priority of that anchor)
        //    also presence of Line Separator to the right of the anchor really drops right anchor priority unless glued to it
        //
        // FIX: need to have flag for attached left or attached right and try to keep that attachment when getting tracked index
    }

    public int getTrackedIndex(@NotNull BasedSequence sequence) {
        // FIX: figure out the location based on current sequence content and offsets
        return markerOffset >= sequence.getStartOffset() && markerOffset <= sequence.getEndOffset() ? markerOffset - sequence.getStartOffset() : -1;
    }

    @Override
    public int compareTo(@NotNull OffsetTracker other) {
        // FIX: compare tracker criteria and return -1 or 0 if this tracker supersedes other or 1 if the other supersedes this one
        int trackedIndex = getTrackedIndex(baseSeq);
        int otherIndex = other.getTrackedIndex(other.baseSeq);
        int thisDelta = 0;
        int otherDelta = 0;
        int result = trackedIndex < 0 ? 1 : otherIndex < 0 ? -1 : 0;

        if (result == 0) {
            int trackedOffset = baseSeq.getStartOffset() + trackedIndex;
            int otherOffset = baseSeq.getStartOffset() + otherIndex;
            thisDelta = markerOffset - trackedOffset;
            otherDelta = markerOffset - otherOffset;

            // compare index offset deviation from original, prefer -ve to positive within reason
            // really need distance to anchors of non-spaces
            switch (myTrackerDirection) {
                default:
                case NONE:
                    // compare index offset deviation from original, prefer -ve to positive within reason
                    // really need distance to anchors of non-spaces, here prefer accumulated error distance to both anchors
                    result = Math.abs(thisDelta) <= Math.abs(otherDelta) ? -1 : 1;
                    break;

                case LEFT:
                    // compare index offset deviation from original, prefer -ve to positive within reason
                    // really need distance to anchors of non-spaces, here prefer distance to left anchor
                    result = Math.abs(thisDelta) <= Math.abs(otherDelta) ? -1 : 1;
                    result = thisDelta <= otherDelta ? -1 : 1;
                    break;

                case RIGHT:
                    // compare index offset deviation from original, prefer -ve to positive within reason
                    // really need distance to anchors of non-spaces, here prefer distance to right anchor
                    result = Math.abs(thisDelta) <= Math.abs(otherDelta) ? -1 : 1;
                    result = thisDelta <= otherDelta ? -1 : 1;
                    break;
            }
        }

        return result;
    }

    @Nullable
    public OffsetTracker modifiedTracker(@NotNull BasedSequence modified, @Nullable OffsetTracker other) {
        // need to take segmented sequence into account because marker index needs to be adjusted to be within the sequence
        // keep marker within modified sequence in the same position relative to non-space characters

        // FIX: after figuring out a new tracker see if the other tracker supersedes ours and return it instead
        OffsetTracker modifiedTracker = null;
        if (baseSeq.containsSomeOf(modified) || modified.containsSomeOf(baseSeq)) {
            // FIX: return new offset tracker based on modifications to sequence
            modifiedTracker = this;
        }

        return other == null ? modifiedTracker : modifiedTracker == null ? other : modifiedTracker.compareTo(other) > 0 ? other : modifiedTracker;
    }

    @Override
    public String toString() {
        return "BasedOffsetTracker{ offset: " + markerOffset + ", index: " + markerIndex + ", dir: " + myTrackerDirection + '}';
    }

    @Nullable
    public static OffsetTracker create(@NotNull BasedSequence baseSeq, int markerIndex, @NotNull TrackerDirection trackerDirection) {
        if (markerIndex < 0 || markerIndex > baseSeq.length()) {
            return null;
        }

        return new OffsetTracker(baseSeq, markerIndex, trackerDirection);
    }
}
