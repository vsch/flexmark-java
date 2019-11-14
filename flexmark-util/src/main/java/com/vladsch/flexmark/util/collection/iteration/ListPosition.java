package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class ListPosition<T> extends ListPositionBase<T,ListPosition<T>> {
    public ListPosition(@NotNull PositionListBase<T, ListPosition<T>> parent, int index, boolean isValid) {
        super(parent, index, isValid);
    }
}
