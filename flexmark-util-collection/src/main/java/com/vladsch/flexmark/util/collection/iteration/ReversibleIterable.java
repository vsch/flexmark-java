package com.vladsch.flexmark.util.collection.iteration;

public interface ReversibleIterable<E> extends Iterable<E> {
  @Override
  ReversibleIterator<E> iterator();

  ReversibleIterable<E> reversed();

  boolean isReversed();

  ReversibleIterator<E> reversedIterator();
}
