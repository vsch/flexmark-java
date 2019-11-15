package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.IPositionBase;
import com.vladsch.flexmark.util.collection.iteration.PositionListBase;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SegmentPosition extends IPositionBase<Object, SegmentPosition> {
    public SegmentPosition(@NotNull PositionListBase<Object, SegmentPosition> parent, int index, boolean isValid) {
        super(parent, index, isValid);
    }

    @Nullable
    public String getStringOrNull() {
        return getOrNull(String.class);
    }

    @Nullable
    public Range getRangeOrNull() {
        return getOrNull(Range.class);
    }

    @NotNull
    public String getString() {
        String string = getOrNull(String.class);
        return string == null ? "" : string;
    }

    @NotNull
    public Range getRange() {
        Range range = getOrNull(Range.class);
        return range == null ? Range.NULL : range;
    }
}
