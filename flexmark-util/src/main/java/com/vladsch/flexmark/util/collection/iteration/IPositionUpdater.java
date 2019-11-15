package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public interface IPositionUpdater<T, P extends IPositionHolder<T, P>> extends IPositionListener {
    void addPositionListener(@NotNull IPositionListener listener);
    void removePositionListener(@NotNull IPositionListener listener);

    @NotNull
    List<T> getList();

    default P getPosition(int index) {
        return getPosition(index, PositionAnchor.NONE);
    }

    P getPosition(int index, @NotNull PositionAnchor anchor);

    void clear();

    Iterator<P> iterator(@NotNull P position);
}
