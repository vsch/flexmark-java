package com.vladsch.flexmark.util.collection.iteration;

interface ReversibleIndexedIterable<E> extends ReversibleIterable<E> {
  @Override
  ReversibleIndexedIterator<E> iterator();

  @Override
  ReversibleIndexedIterable<E> reversed();

  @Override
  ReversibleIndexedIterator<E> reversedIterator();
}
