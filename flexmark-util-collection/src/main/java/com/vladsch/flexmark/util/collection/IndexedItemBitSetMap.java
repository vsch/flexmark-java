package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.function.Function;

public class IndexedItemBitSetMap<K, M> extends IndexedItemSetMapBase<K, BitSet, M> {
    final private @NotNull Function<M, K> computable;

    public IndexedItemBitSetMap(@NotNull Function<M, K> computable) {
        this(computable, 0);
    }

    public IndexedItemBitSetMap(@NotNull Function<M, K> computable, int capacity) {
        super(capacity);
        this.computable = computable;
    }

    public @NotNull Function<M, K> getComputable() {
        return computable;
    }

    @Override
    public @NotNull K mapKey(@NotNull M key) {
        return computable.apply(key);
    }

    @Override
    public @NotNull BitSet newSet() {
        return new BitSet();
    }

    @Override
    public boolean addSetItem(@NotNull BitSet set, int item) {
        boolean old = set.get(item);
        set.set(item);
        return old;
    }

    @Override
    public boolean removeSetItem(@NotNull BitSet set, int item) {
        boolean old = set.get(item);
        set.clear(item);
        return old;
    }

    @Override
    public boolean containsSetItem(@NotNull BitSet set, int item) {
        return set.get(item);
    }
}
