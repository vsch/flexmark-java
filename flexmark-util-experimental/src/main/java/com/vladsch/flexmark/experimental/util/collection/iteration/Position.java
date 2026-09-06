package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;

public class Position<T> extends IPositionBase<T, Position<T>> {
    public Position(IPositionUpdater<T, Position<T>> parent, int index, PositionAnchor anchor) {
        super(parent, index, anchor);
    }
}
