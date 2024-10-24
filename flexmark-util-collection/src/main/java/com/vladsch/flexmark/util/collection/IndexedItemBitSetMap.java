package com.vladsch.flexmark.util.collection;

import java.util.BitSet;
import java.util.function.Function;

class IndexedItemBitSetMap<K, M> extends IndexedItemSetMapBase<K, BitSet, M> {
  private final Function<M, K> computable;

  IndexedItemBitSetMap(Function<M, K> computable) {
    super();
    this.computable = computable;
  }

  @Override
  public K mapKey(M key) {
    return computable.apply(key);
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
