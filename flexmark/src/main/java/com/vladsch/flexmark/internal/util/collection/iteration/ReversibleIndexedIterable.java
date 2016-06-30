package com.vladsch.flexmark.internal.util.collection.iteration;

public interface ReversibleIndexedIterable<E> extends ReversibleIterable<E> {
    @Override
    ReversibleIndexedIterator<E> iterator();
    @Override
    ReversibleIndexedIterable<E> reversed();
    @Override
    ReversibleIndexedIterator<E> reversedIterator();
}
