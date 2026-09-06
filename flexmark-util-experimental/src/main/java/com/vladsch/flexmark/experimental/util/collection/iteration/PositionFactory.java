package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;

public interface PositionFactory<T, P extends IPositionHolder<T, P>> {
    P create(IPositionUpdater<T, P> parent, int index, PositionAnchor anchor);
}
