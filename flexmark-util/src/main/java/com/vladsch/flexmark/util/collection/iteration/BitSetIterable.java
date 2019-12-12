package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class BitSetIterable implements ReversibleIterable<Integer> {
    private final @NotNull BitSet bitSet;
    private final boolean isReversed;

    public BitSetIterable(@NotNull BitSet bitSet) {
        this(bitSet, false);
    }

    public BitSetIterable(@NotNull BitSet bitSet, boolean reversed) {
        this.bitSet = bitSet;
        isReversed = reversed;
    }

    @Override
    public boolean isReversed() {
        return isReversed;
    }

    @NotNull
    @Override
    public ReversibleIterator<Integer> iterator() {
        return new BitSetIterator(bitSet, isReversed);
    }

    @NotNull
    @Override
    public ReversibleIterable<Integer> reversed() {
        return new BitSetIterable(bitSet, !isReversed);
    }

    @NotNull
    @Override
    public ReversibleIterator<Integer> reversedIterator() {
        return new BitSetIterator(bitSet, !isReversed);
    }
}
