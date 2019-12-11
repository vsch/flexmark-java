package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;

public interface PositionFactory<T, P extends IPositionHolder<T, P>> {
    @NotNull
    P create(@NotNull IPositionUpdater<T, P> parent, int index, @NotNull PositionAnchor anchor);
}
