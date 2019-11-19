package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.IPositionBase;
import com.vladsch.flexmark.util.collection.iteration.IPositionUpdater;
import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import org.jetbrains.annotations.NotNull;

public class SegmentPosition extends IPositionBase<Seg, SegmentPosition> {
    public SegmentPosition(@NotNull IPositionUpdater<Seg, SegmentPosition> parent, int index, @NotNull PositionAnchor anchor) {
        super(parent, index, anchor);
    }

    @NotNull
    public Seg getSeg() {
        return getSeg(0);
    }

    @NotNull
    public Seg getSeg(int index) {
        Seg seg = getOrNull(index);
        return seg == null ? Seg.NULL : seg;
    }
}
