package com.vladsch.flexmark.util.collection.iteration;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class IndexedIterator<R, S, I extends ReversibleIterator<Integer>>
    implements ReversibleIndexedIterator<R> {
  private final I iterator;
  private final Indexed<S> items;
  private int lastIndex;
  private int modificationCount;

  public IndexedIterator(@NotNull Indexed<S> items, @NotNull I iterator) {
    this.items = items;
    this.iterator = iterator;
    this.lastIndex = -1;
    this.modificationCount = items.modificationCount();
  }

  @Override
  public boolean isReversed() {
    return iterator.isReversed();
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public @NotNull R next() {
    if (modificationCount != items.modificationCount()) {
      throw new ConcurrentModificationException();
    }

    lastIndex = iterator.next();
    //noinspection unchecked
    return (R) items.get(lastIndex);
  }

  @Override
  public void remove() {
    if (lastIndex == -1) {
      throw new NoSuchElementException();
    }

    if (modificationCount != items.modificationCount()) {
      throw new ConcurrentModificationException();
    }

    items.removeAt(lastIndex);
    lastIndex = -1;
    modificationCount = items.modificationCount();
  }

  @Override
  public int getIndex() {
    if (lastIndex < 0) {
      throw new NoSuchElementException();
    }
    return lastIndex;
  }

  @Override
  public void forEachRemaining(@NotNull Consumer<? super R> consumer) {
    while (hasNext()) {
      consumer.accept(next());
    }
  }
}
