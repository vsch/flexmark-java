package com.vladsch.flexmark.internal.util.collection.iteration;

public interface ReversiblePeekingIterator<E> extends ReversibleIterator<E> {
    E peek();
}
