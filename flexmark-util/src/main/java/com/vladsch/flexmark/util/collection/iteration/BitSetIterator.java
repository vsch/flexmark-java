package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class BitSetIterator implements ReversibleIterator<Integer> {
    private final @NotNull BitSet bitSet;
    private final boolean isReversed;
    private int next;
    private int last;

    public BitSetIterator(@NotNull BitSet bitSet) {
        this(bitSet, false);
    }

    public BitSetIterator(@NotNull BitSet bitSet, boolean reversed) {
        this.bitSet = bitSet;
        isReversed = reversed;
        next = reversed ? bitSet.previousSetBit(bitSet.length()) : bitSet.nextSetBit(0);
        last = -1;
    }

    @Override
    public boolean isReversed() {
        return isReversed;
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
        next = isReversed ? (next == 0 ? -1 : bitSet.previousSetBit(next - 1)) : bitSet.nextSetBit(next + 1);
        return last;
    }

    @Override
    public void remove() {
        if (last == -1) {
            throw new NoSuchElementException();
        }

        bitSet.clear(last);
    }

    public void forEachRemaining(@NotNull Consumer<? super Integer> consumer) {
        while (hasNext()) {
            consumer.accept(next());
        }
    }
}
