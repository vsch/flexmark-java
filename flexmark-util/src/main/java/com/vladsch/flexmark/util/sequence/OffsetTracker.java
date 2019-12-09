package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.PositionAnchor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a tracked offset within a based sequence
 * <p>
 */
public final class OffsetTracker {
    private final int index;
    private final PositionAnchor direction;

    private OffsetTracker(BasedSequence baseSeq, int index, PositionAnchor positionAnchor) {
        this.direction = positionAnchor;
        this.index = index;

        if (this.index < 0 || this.index > baseSeq.length()) {
            throw new IllegalArgumentException("Marker index: " + index + " out of range: [0, " + baseSeq.length() + "], sequence; " + baseSeq);
        }
    }

    public int getIndex() {
        return index;
    }

    public PositionAnchor getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "OffsetTracker{ index: " + index + ", dir: " + direction + '}';
    }

    @NotNull
    public static OffsetTracker create(@NotNull BasedSequence baseSeq, int index, @NotNull PositionAnchor direction) {
        return new OffsetTracker(baseSeq, index, direction);
    }
}
