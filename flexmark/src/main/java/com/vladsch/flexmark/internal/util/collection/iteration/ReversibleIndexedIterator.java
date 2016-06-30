package com.vladsch.flexmark.internal.util.collection.iteration;

public interface ReversibleIndexedIterator<E> extends ReversibleIterator<E> {
    int getIndex();
}
