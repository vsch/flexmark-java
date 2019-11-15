package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.IPositionBase;
import com.vladsch.flexmark.util.collection.iteration.IPositionUpdater;
import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SegmentPosition extends IPositionBase<Object, SegmentPosition> {
    public SegmentPosition(@NotNull IPositionUpdater<Object, SegmentPosition> parent, int index, @NotNull PositionAnchor anchor) {
        super(parent, index, anchor);
    }

    @Nullable
    public String getStringOrNull() {
        return getStringOrNull(0);
    }

    @Nullable
    public Range getRangeOrNull() {
        return getRangeOrNull(0);
    }

    @NotNull
    public String getString() {
        return getString(0);
    }

    @NotNull
    public Range getRange() {
        return getRange(0);
    }

    @Nullable
    public String getStringOrNull(int index) {
        return getOrNull(index, String.class);
    }

    @Nullable
    public Range getRangeOrNull(int index) {
        return getOrNull(index, Range.class);
    }

    @NotNull
    public String getString(int index) {
        String string = getOrNull(index, String.class);
        return string == null ? "" : string;
    }

    @NotNull
    public Range getRange(int index) {
        Range range = getOrNull(index, Range.class);
        return range == null ? Range.EMPTY : range;
    }
}
