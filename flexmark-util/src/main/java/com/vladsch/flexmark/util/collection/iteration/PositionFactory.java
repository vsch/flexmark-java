package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public interface PositionFactory<T, P extends IPosition<T, P>> {
    @NotNull
    P create(@NotNull PositionListBase<T, P> parent, int index, boolean isValid);
}
