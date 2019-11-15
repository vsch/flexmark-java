package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public interface PositionFactory<T, P extends IPositionHolder<T, P>> {
    @NotNull
    P create(@NotNull IPositionUpdater<T, P> parent, int index, @NotNull PositionAnchor anchor);
}
