package com.vladsch.flexmark.internal.util.collection;

public interface ReversiblePeekingIterator<E> extends ReversibleIterator<E> {
    E peek();
}
