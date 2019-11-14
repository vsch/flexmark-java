package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class Position<T> extends IPositionBase<T, Position<T>> {
    public Position(@NotNull PositionListBase<T, Position<T>> parent, int index, boolean isValid) {
        super(parent, index, isValid);
    }
}
