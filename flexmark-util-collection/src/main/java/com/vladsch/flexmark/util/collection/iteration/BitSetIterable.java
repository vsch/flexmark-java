package com.vladsch.flexmark.util.collection.iteration;

import java.util.BitSet;

public class BitSetIterable implements ReversibleIterable<Integer> {
  private final BitSet bitSet;
  private final boolean reversed;

  public BitSetIterable(BitSet bitSet) {
    this(bitSet, false);
  }

  public BitSetIterable(BitSet bitSet, boolean reversed) {
    this.bitSet = bitSet;
    this.reversed = reversed;
  }

  @Override
  public boolean isReversed() {
    return reversed;
  }

  @Override
  public ReversibleIterator<Integer> iterator() {
    return new BitSetIterator(bitSet, reversed);
  }

  @Override
  public ReversibleIterable<Integer> reversed() {
    return new BitSetIterable(bitSet, !reversed);
  }

  @Override
  public ReversibleIterator<Integer> reversedIterator() {
    return new BitSetIterator(bitSet, !reversed);
  }
}
