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

    @NotNull
    public String getString() {
        return getOrNull(String.class);
//        String string = getOrNull(String.class);
//        return string == null ? "" : string;
    }

    @Nullable
    public Range getRange() {
        return getOrNull(Range.class);
//        Range range = getOrNull(Range.class);
//        return range == null ? Range.NULL : range;
    }
}
