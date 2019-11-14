package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public interface ListPositionFactory<T, P extends Position<T, P>> {
    @NotNull
    P create(@NotNull PositionListBase<T, P> parent, int index, boolean isValid);
}
