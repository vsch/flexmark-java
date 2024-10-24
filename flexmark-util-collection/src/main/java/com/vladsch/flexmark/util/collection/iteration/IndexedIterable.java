package com.vladsch.flexmark.util.collection.iteration;

public class IndexedIterable<R, S, I extends ReversibleIterable<Integer>>
    implements ReversibleIndexedIterable<R> {
  private final ReversibleIterable<Integer> iterable;
  private final Indexed<S> items;

  public IndexedIterable(Indexed<S> items, I iterable) {
    this.items = items;
    this.iterable = iterable;
  }

  @Override
  public boolean isReversed() {
    return iterable.isReversed();
  }

  @Override
  public ReversibleIndexedIterator<R> iterator() {
    return new IndexedIterator<>(items, iterable.iterator());
  }

  @Override
  public ReversibleIndexedIterable<R> reversed() {
    return new IndexedIterable<>(items, iterable.reversed());
  }

  @Override
  public ReversibleIndexedIterator<R> reversedIterator() {
    return new IndexedIterator<>(items, iterable.reversedIterator());
  }
}
