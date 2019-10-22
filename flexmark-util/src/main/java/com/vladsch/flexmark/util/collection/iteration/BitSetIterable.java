package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class BitSetIterable implements ReversibleIterable<Integer> {
    private final @NotNull BitSet myBitSet;
    private final boolean myIsReversed;

    public BitSetIterable(@NotNull BitSet bitSet) {
        this(bitSet, false);
    }

    public BitSetIterable(@NotNull BitSet bitSet, boolean reversed) {
        myBitSet = bitSet;
        myIsReversed = reversed;
    }

    @Override
    public boolean isReversed() {
        return myIsReversed;
    }

    @NotNull
    @Override
    public ReversibleIterator<Integer> iterator() {
        return new BitSetIterator(myBitSet, myIsReversed);
    }

    @NotNull
    @Override
    public ReversibleIterable<Integer> reversed() {
        return new BitSetIterable(myBitSet, !myIsReversed);
    }

    @NotNull
    @Override
    public ReversibleIterator<Integer> reversedIterator() {
        return new BitSetIterator(myBitSet, !myIsReversed);
    }
}
