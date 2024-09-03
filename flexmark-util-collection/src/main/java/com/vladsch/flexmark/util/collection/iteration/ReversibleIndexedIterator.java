package com.vladsch.flexmark.util.collection.iteration;

public interface ReversibleIndexedIterator<E> extends ReversibleIterator<E> {
  int getIndex();
}
