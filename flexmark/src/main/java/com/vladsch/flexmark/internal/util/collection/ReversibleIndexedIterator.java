package com.vladsch.flexmark.internal.util.collection;

public interface ReversibleIndexedIterator<E> extends ReversibleIterator<E> {
    int getIndex();
}
