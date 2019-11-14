package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PositionList<T> extends PositionListBase<T, ListPosition<T>> {
    public PositionList(@NotNull List<T> list) {
        super(list, ListPosition::new);
    }
}
