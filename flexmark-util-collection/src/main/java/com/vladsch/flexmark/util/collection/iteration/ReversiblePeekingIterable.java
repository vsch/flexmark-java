package com.vladsch.flexmark.util.collection.iteration;

public interface ReversiblePeekingIterable<E> extends ReversibleIterable<E> {
  @Override
  ReversiblePeekingIterator<E> iterator();

  @Override
  ReversiblePeekingIterable<E> reversed();

  @Override
  ReversiblePeekingIterator<E> reversedIterator();
}
