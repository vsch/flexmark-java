package com.vladsch.flexmark.util.collection.iteration;

public class IndexedIterable<R, S, I extends ReversibleIterable<Integer>> implements ReversibleIndexedIterable<R> {
    private final ReversibleIterable<Integer> myIterable;
    private final Indexed<S> myItems;

    public IndexedIterable(Indexed<S> items, I iterable) {
        this.myItems = items;
        this.myIterable = iterable;
    }

    @Override
    public boolean isReversed() {
        return myIterable.isReversed();
    }

    @Override
    public ReversibleIndexedIterator<R> iterator() {
        return new IndexedIterator<>(myItems, myIterable.iterator());
    }

    @Override
    public ReversibleIndexedIterable<R> reversed() {
        return new IndexedIterable<>(myItems, myIterable.reversed());
    }

    @Override
    public ReversibleIndexedIterator<R> reversedIterator() {
        return new IndexedIterator<>(myItems, myIterable.reversedIterator());
    }
}
