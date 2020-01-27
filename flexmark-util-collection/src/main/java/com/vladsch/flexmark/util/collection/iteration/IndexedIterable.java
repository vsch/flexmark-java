package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class IndexedIterable<R, S, I extends ReversibleIterable<Integer>> implements ReversibleIndexedIterable<R> {
    final private @NotNull ReversibleIterable<Integer> iterable;
    final private @NotNull Indexed<S> items;

    public IndexedIterable(@NotNull Indexed<S> items, @NotNull I iterable) {
        this.items = items;
        this.iterable = iterable;
    }

    @Override
    public boolean isReversed() {
        return iterable.isReversed();
    }

    @NotNull
    @Override
    public ReversibleIndexedIterator<R> iterator() {
        return new IndexedIterator<>(items, iterable.iterator());
    }

    @NotNull
    @Override
    public ReversibleIndexedIterable<R> reversed() {
        return new IndexedIterable<>(items, iterable.reversed());
    }

    @NotNull
    @Override
    public ReversibleIndexedIterator<R> reversedIterator() {
        return new IndexedIterator<>(items, iterable.reversedIterator());
    }
}
