package com.vladsch.flexmark.util.collection.iteration;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Reverse<T> implements ReversibleIterable<T> {
  private final @NotNull List<T> list;
  private final boolean isReversed;

  public Reverse(@NotNull List<T> list) {
    this(list, true);
  }

  private Reverse(@NotNull List<T> list, boolean isReversed) {
    this.list = list;
    this.isReversed = isReversed;
  }

  private static class ReversedListIterator<T> implements ReversibleIterator<T> {
    private final @NotNull List<T> list;
    private final boolean isReversed;
    private int index;

    ReversedListIterator(@NotNull List<T> list, boolean isReversed) {
      this.list = list;
      this.isReversed = isReversed;
      if (isReversed) {
        this.index = list.isEmpty() ? -1 : list.size() - 1;
      } else {
        this.index = list.isEmpty() ? -1 : 0;
      }
    }

    @Override
    public boolean isReversed() {
      return isReversed;
    }

    @Override
    public void remove() {}

    @Override
    public boolean hasNext() {
      return index != -1;
    }

    @Override
    public T next() {
      T t = list.get(index);
      if (index != -1) {
        if (isReversed) {
          index--;
        } else {
          if (index == list.size() - 1) {
            index = -1;
          } else {
            index++;
          }
        }
      }

      return t;
    }
  }

  @NotNull
  @Override
  public ReversibleIterator<T> iterator() {
    return new ReversedListIterator<>(list, isReversed);
  }

  @NotNull
  @Override
  public ReversibleIterable<T> reversed() {
    return new Reverse<>(list, !isReversed);
  }

  @Override
  public boolean isReversed() {
    return isReversed;
  }

  @NotNull
  @Override
  public ReversibleIterator<T> reversedIterator() {
    return new ReversedListIterator<>(list, !isReversed);
  }
}
