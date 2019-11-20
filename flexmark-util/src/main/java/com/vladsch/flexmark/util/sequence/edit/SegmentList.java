package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionListBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SegmentList extends PositionListBase<Seg, SegmentPosition> {
    public SegmentList() {
        super(new ArrayList<>(), SegmentPosition::new);
    }

    public SegmentList(ArrayList<Seg> parts) {
        super(parts, SegmentPosition::new);
    }

    @NotNull
    public Seg getSeg(int index) {
        Seg seg = getOrNull(index);
        return seg == null ? Seg.NULL : seg;
    }
}
