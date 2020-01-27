package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class IndexedItemIterable<R> implements ReversibleIndexedIterable<R> {
    final private @NotNull Indexed<R> items;
    final private boolean reversed;

    public IndexedItemIterable(@NotNull Indexed<R> items) {
        this(items, false);
    }

    public IndexedItemIterable(@NotNull Indexed<R> items, boolean reversed) {
        this.items = items;
        this.reversed = reversed;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @NotNull
    @Override
    public ReversibleIndexedIterator<R> iterator() {
        return new IndexedItemIterator<>(items, reversed);
    }

    @NotNull
    @Override
    public ReversibleIndexedIterable<R> reversed() {
        return new IndexedItemIterable<>(items, !reversed);
    }

    @NotNull
    @Override
    public ReversibleIndexedIterator<R> reversedIterator() {
        return new IndexedItemIterator<>(items, !reversed);
    }
}
