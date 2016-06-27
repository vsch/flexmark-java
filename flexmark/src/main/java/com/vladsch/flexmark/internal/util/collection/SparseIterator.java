package com.vladsch.flexmark.internal.util.collection;

public interface SparseIterator<E> extends ReversibleIterator<E> {
    @Override SparseIterator<E> reversed();
    int getIndex();
}
