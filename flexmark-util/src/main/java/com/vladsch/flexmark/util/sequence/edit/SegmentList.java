package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionListBase;

import java.util.ArrayList;

public class SegmentList extends PositionListBase<Object, SegmentPosition> {
    public SegmentList() {
        super(new ArrayList<>(), SegmentPosition::new);
    }
}
