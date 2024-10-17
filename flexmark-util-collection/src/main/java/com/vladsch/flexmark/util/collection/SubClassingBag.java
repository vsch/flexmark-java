package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class SubClassingBag<T> {
  private final @NotNull ClassificationBag<Class<?>, T> items;
  private final @NotNull HashMap<Class<?>, BitSet> subClassMap;

  public SubClassingBag(
      @NotNull ClassificationBag<Class<?>, T> items,
      Map<Class<?>, @NotNull List<Class<?>>> subClassMap) {
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

  public final <X> @NotNull ReversibleIterable<X> itemsOfType(
      @NotNull Class<X> xClass, @NotNull Class<?>... categories) {
    return items.getCategoryItems(typeBitSet(xClass, categories));
  }

  public final <X> @NotNull ReversibleIterable<X> itemsOfType(
      @NotNull Class<X> xClass, @NotNull Collection<Class<?>> categories) {
    return items.getCategoryItems(typeBitSet(xClass, categories));
  }

  public final <X> @NotNull ReversibleIterable<X> reversedItemsOfType(
      @NotNull Class<X> xClass, @NotNull Class<?>... categories) {
    return items.getCategoryItemsReversed(typeBitSet(xClass, categories));
  }

  public final <X> @NotNull ReversibleIterable<X> reversedItemsOfType(
      @NotNull Class<X> xClass, @NotNull Collection<Class<?>> categories) {
    return items.getCategoryItemsReversed(typeBitSet(xClass, categories));
  }

  private final @NotNull BitSet typeBitSet(
      @NotNull Class<?> xClass, @NotNull Class<?>... categories) {
    BitSet bitSet = new BitSet();
    for (Class<?> category : categories) {
      if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
        bitSet.or(subClassMap.get(category));
      }
    }
    return bitSet;
  }

  private final @NotNull BitSet typeBitSet(
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
