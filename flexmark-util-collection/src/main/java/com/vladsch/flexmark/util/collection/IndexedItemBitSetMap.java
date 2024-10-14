package com.vladsch.flexmark.util.collection;

import java.util.BitSet;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

class IndexedItemBitSetMap<K, M> extends IndexedItemSetMapBase<K, BitSet, M> {
  private final @NotNull Function<M, K> computable;

  IndexedItemBitSetMap(@NotNull Function<M, K> computable) {
    this(computable, 0);
  }

  private IndexedItemBitSetMap(@NotNull Function<M, K> computable, int capacity) {
    super(capacity);
    this.computable = computable;
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
