package com.vladsch.flexmark.experimental.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PositionList<T> extends PositionListBase<T, Position<T>> {
    public PositionList(@NotNull List<T> list) {
        super(list, Position::new);
    }
}
