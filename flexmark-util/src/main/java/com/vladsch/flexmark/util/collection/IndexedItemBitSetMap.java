package com.vladsch.flexmark.util.collection;

import java.util.BitSet;
import java.util.function.Function;

public class IndexedItemBitSetMap<K, M> extends IndexedItemSetMapBase<K, BitSet, M> {
    private final Function<M, K> myComputable;

    public IndexedItemBitSetMap(Function<M, K> computable) {
        this(computable, 0);
    }

    public IndexedItemBitSetMap(Function<M, K> computable, int capacity) {
        super(capacity);
        this.myComputable = computable;
    }

    public Function<M, K> getComputable() {
        return myComputable;
    }

    @Override
    public K mapKey(M key) {
        return myComputable.apply(key);
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
