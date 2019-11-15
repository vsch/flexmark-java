package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class Position<T> extends IPositionBase<T, Position<T>> {
    public Position(@NotNull IPositionUpdater<T, Position<T>> parent, int index, @NotNull PositionAnchor anchor) {
        super(parent, index, anchor);
    }
}
