package com.vladsch.flexmark.util.collection.iteration;

public interface ReversiblePeekingIterator<E> extends ReversibleIterator<E> {

  E peek();
}
