package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.BitSetIterable;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import java.util.BitSet;
import java.util.Collection;
import java.util.function.Function;

public class ClassificationBag<K, V> {
  private final OrderedSet<V> items;
  private final IndexedItemBitSetMap<K, V> bag;
  private final CollectionHost<V> host;

  public ClassificationBag(Function<V, K> mapper) {
    this(0, mapper);
  }

  private ClassificationBag(int capacity, Function<V, K> mapper) {
    this(capacity, mapper, null);
  }

  private ClassificationBag(int capacity, Function<V, K> mapper, CollectionHost<V> host) {
    this.host = host;
    this.items =
        new OrderedSet<>(
            capacity,
            new CollectionHost<V>() {
              @Override
              public void adding(int index, V v, Object v2) {
                if (ClassificationBag.this.host != null
                    && !ClassificationBag.this.host.skipHostUpdate())
                  ClassificationBag.this.host.adding(index, v, v2);
                if (v != null) bag.addItem(v, index);
              }

              @Override
              public Object removing(int index, V v) {
                if (ClassificationBag.this.host != null
                    && !ClassificationBag.this.host.skipHostUpdate())
                  ClassificationBag.this.host.removing(index, v);
                if (v != null) bag.removeItem(v, index);
                return null;
              }

              @Override
              public void clearing() {
                if (ClassificationBag.this.host != null
                    && !ClassificationBag.this.host.skipHostUpdate())
                  ClassificationBag.this.host.clearing();
                bag.clear();
              }

              @Override
              public void addingNulls(int index) {
                // nothing to be done, we're good
                if (ClassificationBag.this.host != null
                    && !ClassificationBag.this.host.skipHostUpdate())
                  ClassificationBag.this.host.addingNulls(index);
              }

              @Override
              public boolean skipHostUpdate() {
                return false;
              }

              @Override
              public int getIteratorModificationCount() {
                return getModificationCount();
              }
            });

    this.bag = new IndexedItemBitSetMap<>(mapper);
  }

  public OrderedSet<V> getItems() {
    return items;
  }

  public int getModificationCount() {
    return items.getModificationCount();
  }

  public boolean add(V item) {
    return items.add(item);
  }

  public boolean remove(V item) {
    return items.remove(item);
  }

  public boolean containsCategory(K category) {
    BitSet bitSet = bag.get(category);
    return bitSet != null && !bitSet.isEmpty();
  }

  int getCategoryCount(K category) {
    BitSet bitSet = bag.get(category);
    return bitSet == null ? 0 : bitSet.cardinality();
  }

  public void clear() {
    items.clear();
  }

  @SafeVarargs
  public final <X> ReversibleIterable<X> getCategoryItems(K... categories) {
    return new IndexedIterable<>(
        items.getConcurrentModsIndexedProxy(),
        new BitSetIterable(categoriesBitSet(categories), false));
  }

  public final <X> ReversibleIterable<X> getCategoryItems(Collection<? extends K> categories) {
    return new IndexedIterable<>(
        items.getConcurrentModsIndexedProxy(),
        new BitSetIterable(categoriesBitSet(categories), false));
  }

  final <X> ReversibleIterable<X> getCategoryItems(BitSet bitSet) {
    return new IndexedIterable<>(
        items.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, false));
  }

  final <X> ReversibleIterable<X> getCategoryItemsReversed(BitSet bitSet) {
    return new IndexedIterable<>(
        items.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, true));
  }

  @SafeVarargs
  private final BitSet categoriesBitSet(K... categories) {
    BitSet bitSet = new BitSet();
    for (K category : categories) {
      BitSet bitSet1 = bag.get(category);
      if (bitSet1 != null) {
        bitSet.or(bitSet1);
      }
    }
    return bitSet;
  }

  public final BitSet categoriesBitSet(Collection<? extends K> categories) {
    BitSet bitSet = new BitSet();
    for (K category : categories) {
      BitSet bitSet1 = bag.get(category);
      if (bitSet1 != null) {
        bitSet.or(bitSet1);
      }
    }
    return bitSet;
  }
}
