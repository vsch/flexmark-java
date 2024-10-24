package com.vladsch.flexmark.util.collection.iteration;

import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class BitSetIterator implements ReversibleIterator<Integer> {
  private final BitSet bitSet;
  private final boolean reversed;
  private int next;
  private int last;

  public BitSetIterator(BitSet bitSet) {
    this(bitSet, false);
  }

  public BitSetIterator(BitSet bitSet, boolean reversed) {
    this.bitSet = bitSet;
    this.reversed = reversed;
    next = reversed ? bitSet.previousSetBit(bitSet.length()) : bitSet.nextSetBit(0);
    last = -1;
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
  public Integer next() {
    if (next == -1) {
      throw new NoSuchElementException();
    }

    last = next;
    next =
        reversed ? (next == 0 ? -1 : bitSet.previousSetBit(next - 1)) : bitSet.nextSetBit(next + 1);
    return last;
  }

  @Override
  public void remove() {
    if (last == -1) {
      throw new NoSuchElementException();
    }

    bitSet.clear(last);
  }

  @Override
  public void forEachRemaining(Consumer<? super Integer> consumer) {
    while (hasNext()) {
      consumer.accept(next());
    }
  }
}
