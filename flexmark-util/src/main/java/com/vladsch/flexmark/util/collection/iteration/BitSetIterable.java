package com.vladsch.flexmark.util.collection.iteration;

import java.util.BitSet;

public class BitSetIterable implements ReversibleIterable<Integer> {
    private final BitSet myBitSet;
    private final boolean myIsReversed;

    public BitSetIterable(BitSet bitSet) {
        this(bitSet, false);
    }

    public BitSetIterable(BitSet bitSet, boolean reversed) {
        myBitSet = bitSet;
        myIsReversed = reversed;
    }

    @Override
    public boolean isReversed() {
        return myIsReversed;
    }

    @Override
    public ReversibleIterator<Integer> iterator() {
        return new BitSetIterator(myBitSet, myIsReversed);
    }

    @Override
    public ReversibleIterable<Integer> reversed() {
        return new BitSetIterable(myBitSet, !myIsReversed);
    }

    @Override
    public ReversibleIterator<Integer> reversedIterator() {
        return new BitSetIterator(myBitSet, !myIsReversed);
    }
}
