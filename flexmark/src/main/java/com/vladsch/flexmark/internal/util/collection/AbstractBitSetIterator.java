package com.vladsch.flexmark.internal.util.collection;

import java.util.BitSet;

public abstract class AbstractBitSetIterator<E> extends AbstractSparseIterator<E> {
    final protected BitSet bitSet;
    final protected boolean bitTypes;

    protected abstract int maxSize();
    protected abstract void remove(int index);

    public AbstractBitSetIterator(BitSet bitSet, boolean bitTypes, boolean reversed) {
        super(reversed);
        this.bitSet = bitSet;
        this.bitTypes = bitTypes;
    }

    @Override
    protected int nextValidIndex(int index) {
        return bitTypes ? bitSet.nextSetBit(index) : bitSet.nextClearBit(index);
    }

    @Override
    protected int previousValidIndex(int index) {
        return bitTypes ? bitSet.previousSetBit(index) : bitSet.previousClearBit(index);
    }
}
