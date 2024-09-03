package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SubClassingBag<T> {
  private final @NotNull ClassificationBag<Class<?>, T> items;
  private final @NotNull HashMap<Class<?>, BitSet> subClassMap;

  public SubClassingBag(
      @NotNull ClassificationBag<Class<?>, T> items,
      HashMap<Class<?>, @NotNull List<Class<?>>> subClassMap) {
    this.items = items;
    this.subClassMap = new HashMap<>();

    for (Class<?> clazz : subClassMap.keySet()) {
      List<Class<?>> classList = subClassMap.get(clazz);
      BitSet bitSet = this.items.categoriesBitSet(classList);
      if (!bitSet.isEmpty()) {
        this.subClassMap.put(clazz, bitSet);
      }
    }
  }

  public @NotNull OrderedSet<T> getItems() {
    return items.getItems();
  }

  public boolean contains(@Nullable T item) {
    return items.contains(item);
  }

  public boolean containsType(@Nullable Class<?> type) {
    return items.containsCategory(type);
  }

  public BitSet getTypeSet(@Nullable Class<?> category) {
    return subClassMap.get(category);
  }

  public int getTypeCount(@Nullable Class<?> category) {
    BitSet bitSet = subClassMap.get(category);
    return bitSet == null ? 0 : bitSet.cardinality();
  }

  public final <X> @NotNull ReversibleIterable<X> itemsOfType(
      @NotNull Class<X> xClass, @NotNull Class<?>... categories) {
    return items.getCategoryItems(xClass, typeBitSet(xClass, categories));
  }

  public final <X> @NotNull ReversibleIterable<X> itemsOfType(
      @NotNull Class<X> xClass, @NotNull Collection<Class<?>> categories) {
    return items.getCategoryItems(xClass, typeBitSet(xClass, categories));
  }

  public final <X> @NotNull ReversibleIterable<X> reversedItemsOfType(
      @NotNull Class<X> xClass, @NotNull Class<?>... categories) {
    return items.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
  }

  public final <X> @NotNull ReversibleIterable<X> reversedItemsOfType(
      @NotNull Class<X> xClass, @NotNull Collection<Class<?>> categories) {
    return items.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
  }

  public final @NotNull BitSet typeBitSet(
      @NotNull Class<?> xClass, @NotNull Class<?>... categories) {
    BitSet bitSet = new BitSet();
    for (Class<?> category : categories) {
      if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
        bitSet.or(subClassMap.get(category));
      }
    }
    return bitSet;
  }

  public final @NotNull BitSet typeBitSet(
      @NotNull Class<?> xClass, @NotNull Collection<Class<?>> categories) {
    BitSet bitSet = new BitSet();
    for (Class<?> category : categories) {
      if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
        bitSet.or(subClassMap.get(category));
      }
    }
    return bitSet;
  }
}
