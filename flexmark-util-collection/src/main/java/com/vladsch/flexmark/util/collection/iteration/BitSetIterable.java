package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class BitSetIterable implements ReversibleIterable<Integer> {
    final private @NotNull BitSet bitSet;
    final private boolean reversed;

    public BitSetIterable(@NotNull BitSet bitSet) {
        this(bitSet, false);
    }

    public BitSetIterable(@NotNull BitSet bitSet, boolean reversed) {
        this.bitSet = bitSet;
        this.reversed = reversed;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @NotNull
    @Override
    public ReversibleIterator<Integer> iterator() {
        return new BitSetIterator(bitSet, reversed);
    }

    @NotNull
    @Override
    public ReversibleIterable<Integer> reversed() {
        return new BitSetIterable(bitSet, !reversed);
    }

    @NotNull
    @Override
    public ReversibleIterator<Integer> reversedIterator() {
        return new BitSetIterator(bitSet, !reversed);
    }
}
