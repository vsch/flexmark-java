package com.vladsch.flexmark.util.collection.iteration;

public class IndexedItemIterable<R> implements ReversibleIndexedIterable<R> {
  private final Indexed<R> items;
  private final boolean reversed;

  public IndexedItemIterable(Indexed<R> items) {
    this(items, false);
  }

  private IndexedItemIterable(Indexed<R> items, boolean reversed) {
    this.items = items;
    this.reversed = reversed;
  }

  @Override
  public boolean isReversed() {
    return reversed;
  }

  @Override
  public ReversibleIndexedIterator<R> iterator() {
    return new IndexedItemIterator<>(items, reversed);
  }

  @Override
  public ReversibleIndexedIterable<R> reversed() {
    return new IndexedItemIterable<>(items, !reversed);
  }

  @Override
  public ReversibleIndexedIterator<R> reversedIterator() {
    return new IndexedItemIterator<>(items, !reversed);
  }
}
