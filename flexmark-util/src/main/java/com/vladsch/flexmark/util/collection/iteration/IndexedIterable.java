package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class IndexedIterable<R, S, I extends ReversibleIterable<Integer>> implements ReversibleIndexedIterable<R> {
    private final @NotNull ReversibleIterable<Integer> myIterable;
    private final @NotNull Indexed<S> myItems;

    public IndexedIterable(@NotNull Indexed<S> items, @NotNull I iterable) {
        this.myItems = items;
        this.myIterable = iterable;
    }

    @Override
    public boolean isReversed() {
        return myIterable.isReversed();
    }

    @NotNull
    @Override
    public ReversibleIndexedIterator<R> iterator() {
        return new IndexedIterator<>(myItems, myIterable.iterator());
    }

    @NotNull
    @Override
    public ReversibleIndexedIterable<R> reversed() {
        return new IndexedIterable<>(myItems, myIterable.reversed());
    }

    @NotNull
    @Override
    public ReversibleIndexedIterator<R> reversedIterator() {
        return new IndexedIterator<>(myItems, myIterable.reversedIterator());
    }
}
