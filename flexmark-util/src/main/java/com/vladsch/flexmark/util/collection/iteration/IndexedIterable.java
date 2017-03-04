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
        return new IndexedIterator<R, S, ReversibleIterator<Integer>>(myItems, myIterable.iterator());
    }

    @Override
    public ReversibleIndexedIterable<R> reversed() {
        return new IndexedIterable<R, S, ReversibleIterable<Integer>>(myItems, myIterable.reversed());
    }

    @Override
    public ReversibleIndexedIterator<R> reversedIterator() {
        return new IndexedIterator<R, S, ReversibleIterator<Integer>>(myItems, myIterable.reversedIterator());
    }
}
