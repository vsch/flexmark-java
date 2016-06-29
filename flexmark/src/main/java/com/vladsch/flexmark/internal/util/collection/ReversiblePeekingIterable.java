package com.vladsch.flexmark.internal.util.collection;

public interface ReversiblePeekingIterable<E> extends ReversibleIterable<E> {
    @Override ReversiblePeekingIterator<E> iterator();
    @Override ReversiblePeekingIterable<E> reversed();
    @Override ReversiblePeekingIterator<E> reversedIterator();
}
