package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.BitSetIterable;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import java.util.BitSet;
import java.util.Collection;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClassificationBag<K, V> {
  private final @NotNull OrderedSet<V> items;
  private final @NotNull IndexedItemBitSetMap<K, V> bag;
  private final @Nullable CollectionHost<V> host;

  public ClassificationBag(Function<V, K> mapper) {
    this(0, mapper);
  }

  private ClassificationBag(int capacity, @NotNull Function<V, K> mapper) {
    this(capacity, mapper, null);
  }

  private ClassificationBag(
      int capacity, @NotNull Function<V, K> mapper, @Nullable CollectionHost<V> host) {
    this.host = host;
    this.items =
        new OrderedSet<>(
            capacity,
            new CollectionHost<V>() {
              @Override
              public void adding(int index, @Nullable V v, @Nullable Object v2) {
                if (ClassificationBag.this.host != null
                    && !ClassificationBag.this.host.skipHostUpdate())
                  ClassificationBag.this.host.adding(index, v, v2);
                if (v != null) bag.addItem(v, index);
              }

              @Override
              public Object removing(int index, @Nullable V v) {
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

  @NotNull
  public OrderedSet<V> getItems() {
    return items;
  }

  public int getModificationCount() {
    return items.getModificationCount();
  }

  public boolean add(@Nullable V item) {
    return items.add(item);
  }

  public boolean remove(@Nullable V item) {
    return items.remove(item);
  }

  public boolean containsCategory(@Nullable K category) {
    BitSet bitSet = bag.get(category);
    return bitSet != null && !bitSet.isEmpty();
  }

  int getCategoryCount(@Nullable K category) {
    BitSet bitSet = bag.get(category);
    return bitSet == null ? 0 : bitSet.cardinality();
  }

  public void clear() {
    items.clear();
  }

  @SafeVarargs
  public final <X> @NotNull ReversibleIterable<X> getCategoryItems(
      @NotNull Class<? extends X> xClass, @NotNull K... categories) {
    return new IndexedIterable<X, V, ReversibleIterable<Integer>>(
        items.getConcurrentModsIndexedProxy(),
        new BitSetIterable(categoriesBitSet(categories), false));
  }

  public final <X> @NotNull ReversibleIterable<X> getCategoryItems(
      @NotNull Class<? extends X> xClass, @NotNull Collection<? extends K> categories) {
    return new IndexedIterable<X, V, ReversibleIterable<Integer>>(
        items.getConcurrentModsIndexedProxy(),
        new BitSetIterable(categoriesBitSet(categories), false));
  }

  final <X> @NotNull ReversibleIterable<X> getCategoryItems(
      @NotNull Class<? extends X> xClass, @NotNull BitSet bitSet) {
    return new IndexedIterable<X, V, ReversibleIterable<Integer>>(
        items.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, false));
  }

  final <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(
      @NotNull Class<? extends X> xClass, @NotNull BitSet bitSet) {
    return new IndexedIterable<X, V, ReversibleIterable<Integer>>(
        items.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, true));
  }

  @SafeVarargs
  private final @NotNull BitSet categoriesBitSet(@NotNull K... categories) {
    BitSet bitSet = new BitSet();
    for (K category : categories) {
      BitSet bitSet1 = bag.get(category);
      if (bitSet1 != null) {
        bitSet.or(bitSet1);
      }
    }
    return bitSet;
  }

  public final @NotNull BitSet categoriesBitSet(@NotNull Collection<? extends K> categories) {
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
