package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.Computable;

import java.util.BitSet;

public class IndexedItemBitSetMap<K, M> extends IndexedItemSetMapBase<K, BitSet, M> {
    private final Computable<K, M> myComputable;

    public IndexedItemBitSetMap(Computable<K, M> computable) {
        this(computable, 0);
    }

    public IndexedItemBitSetMap(Computable<K, M> computable, int capacity) {
        super(capacity);
        this.myComputable = computable;
    }

    public Computable<K, M> getComputable() {
        return myComputable;
    }

    @Override
    public K mapKey(M key) {
        return myComputable.compute(key);
    }

    @Override
    public BitSet newSet() {
        return new BitSet();
    }

    @Override
    public boolean addSetItem(BitSet set, int item) {
        boolean old = set.get(item);
        set.set(item);
        return old;
    }

    @Override
    public boolean removeSetItem(BitSet set, int item) {
        boolean old = set.get(item);
        set.clear(item);
        return old;
    }

    @Override
    public boolean containsSetItem(BitSet set, int item) {
        return set.get(item);
    }
}
