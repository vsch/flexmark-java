package com.vladsch.flexmark.internal.util.collection;

public class BitSetCollectingBag<K> extends CollectingBag<K, CountingBitSet, Integer> {

    public BitSetCollectingBag() {
        this(0);
    }

    public BitSetCollectingBag(int capacity) {
        super(capacity);
    }

    @Override
    protected CountingBitSet newSet() {
        return new CountingBitSet();
    }

    @Override
    protected boolean addSetItem(CountingBitSet s, Integer item) {
        boolean old = s.get(item);
        s.set(item);
        return !old;
    }

    @Override
    protected boolean removeSetItem(CountingBitSet s, Integer item) {
        boolean old = s.get(item);
        s.clear(item);
        return old;
    }

    @Override
    protected boolean containsSetItem(CountingBitSet s, Integer item) {
        return s.get(item);
    }
}
