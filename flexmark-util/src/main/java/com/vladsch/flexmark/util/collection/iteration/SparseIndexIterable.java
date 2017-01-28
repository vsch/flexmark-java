package com.vladsch.flexmark.util.collection.iteration;

import java.util.BitSet;

/**
 * @deprecated not tested
 */
public class SparseIndexIterable implements ReversibleIterable<Integer> {
    private final int[] myStarts;
    private final int[] myEnds;
    private final boolean myIsReversed;

    public SparseIndexIterable(BitSet bitSet) {
        this(bitSet, false);
    }

    public SparseIndexIterable(BitSet bitSet, boolean reversed) {
        int i = 0;
        int lastIndex = 0;
        while (true) {
            int start = bitSet.nextSetBit(lastIndex);
            if (start < 0) break;

            int end = bitSet.nextClearBit(start);
            i++;
            lastIndex = end;
        }

        myStarts = new int[i];
        myEnds = new int[i];

        lastIndex = 0;
        i = 0;
        while (true) {
            int start = bitSet.nextSetBit(lastIndex);
            if (start < 0) break;
            int end = bitSet.nextClearBit(start);
            myStarts[i] = start;
            myEnds[i] = end - 1;
            i++;
            lastIndex = end;
        }

        myIsReversed = reversed;
    }

    public SparseIndexIterable(int[] starts, int[] ends, boolean reversed) {
        myStarts = starts;
        myEnds = ends;
        myIsReversed = reversed;
        if (starts.length != ends.length)
            throw new IllegalArgumentException("starts and ends arrays must be the same size");
    }

    @Override
    public boolean isReversed() {
        return myIsReversed;
    }

    @Override
    public ReversibleIterator<Integer> iterator() {
        return new SparseIndexIterator(myStarts, myEnds, myIsReversed);
    }

    @Override
    public ReversibleIterable<Integer> reversed() {
        return new SparseIndexIterable(myStarts, myEnds, !myIsReversed);
    }

    @Override
    public ReversibleIterator<Integer> reversedIterator() {
        return new SparseIndexIterator(myStarts, myEnds, !myIsReversed);
    }
}
