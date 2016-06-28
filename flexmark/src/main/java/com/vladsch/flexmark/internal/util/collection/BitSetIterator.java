package com.vladsch.flexmark.internal.util.collection;

import java.util.BitSet;

public class BitSetIterator extends AbstractBitSetIterator<Integer> {
    final private int maxSize;

    public BitSetIterator(BitSet bitSet) {
        this(bitSet, false);
    }

    public BitSetIterator(BitSet bitSet, boolean bitTypes) {
        this(bitSet, bitTypes, false);
    }

    public BitSetIterator(BitSet bitSet, boolean bitTypes, boolean reversed) {
        this(bitSet, -1, bitTypes, reversed);
    }

    public BitSetIterator(BitSet bitSet, int maxSize, boolean bitTypes, boolean reversed) {
        super(bitSet, bitTypes, reversed);
        this.maxSize = maxSize;
    }

    @Override
    protected void checkConcurrentMods() {
        
    }

    @Override
    protected int maxSize() {
        return maxSize >= 0 ? maxSize : bitSet.size();
    }

    @Override
    protected void remove(int index) {
        if (bitTypes) {
            bitSet.clear(index);
        } else {
            bitSet.set(index);
        }
    }

    @Override
    public SparseIterator<Integer> reversed() {
        return new BitSetIterator(bitSet, maxSize, bitTypes, !isReversed());
    }

    @Override
    protected Integer getValueAt(int index) {
        return index;
    }

}
