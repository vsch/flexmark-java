package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionListBase;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SegmentList extends PositionListBase<Object, SegmentPosition> {
    public SegmentList() {
        super(new ArrayList<>(), SegmentPosition::new);
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
        String value = getOrNull(index, String.class);
        return value != null ? value : "";
    }

    @NotNull
    public Range getRange(int index) {
        Range value = getOrNull(index, Range.class);
        return value != null ? value : Range.NULL;
    }
}
