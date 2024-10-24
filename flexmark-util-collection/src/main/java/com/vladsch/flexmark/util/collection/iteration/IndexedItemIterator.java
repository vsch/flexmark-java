package com.vladsch.flexmark.util.collection.iteration;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class IndexedItemIterator<R> implements ReversibleIndexedIterator<R> {
  private final Indexed<R> items;
  private final boolean reversed;
  private int next;
  private int last;
  private int modificationCount;

  public IndexedItemIterator(Indexed<R> items) {
    this(items, false);
  }

  IndexedItemIterator(Indexed<R> items, boolean isReversed) {
    this.items = items;
    reversed = isReversed;
    next = reversed ? items.size() - 1 : 0;

    // empty forward iterator has no next
    if (next >= items.size()) next = -1;

    last = -1;
    this.modificationCount = items.modificationCount();
  }

  @Override
  public boolean isReversed() {
    return reversed;
  }

  @Override
  public boolean hasNext() {
    return next != -1;
  }

  @Override
  public R next() {
    if (modificationCount != items.modificationCount()) {
      throw new ConcurrentModificationException();
    }

    if (next == -1) {
      throw new NoSuchElementException();
    }

    last = next;
    next = reversed ? (next <= 0 ? -1 : next - 1) : next == items.size() - 1 ? -1 : next + 1;
    return items.get(last);
  }

  @Override
  public void remove() {
    if (last == -1) {
      throw new NoSuchElementException();
    }

    if (modificationCount != items.modificationCount()) {
      throw new ConcurrentModificationException();
    }

    items.removeAt(last);
    last = -1;
    modificationCount = items.modificationCount();
  }

  @Override
  public int getIndex() {
    if (last < 0) {
      throw new NoSuchElementException();
    }
    return last;
  }

  @Override
  public void forEachRemaining(Consumer<? super R> consumer) {
    while (hasNext()) {
      consumer.accept(next());
    }
  }
}
