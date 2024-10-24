package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubClassingBag<T> {
  private final ClassificationBag<Class<?>, T> items;
  private final HashMap<Class<?>, BitSet> subClassMap;

  public SubClassingBag(
      ClassificationBag<Class<?>, T> items, Map<Class<?>, List<Class<?>>> subClassMap) {
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

  public final <X> ReversibleIterable<X> itemsOfType(Class<X> xClass, Class<?>... categories) {
    return items.getCategoryItems(typeBitSet(xClass, categories));
  }

  public final <X> ReversibleIterable<X> itemsOfType(
      Class<X> xClass, Collection<Class<?>> categories) {
    return items.getCategoryItems(typeBitSet(xClass, categories));
  }

  public final <X> ReversibleIterable<X> reversedItemsOfType(
      Class<X> xClass, Class<?>... categories) {
    return items.getCategoryItemsReversed(typeBitSet(xClass, categories));
  }

  public final <X> ReversibleIterable<X> reversedItemsOfType(
      Class<X> xClass, Collection<Class<?>> categories) {
    return items.getCategoryItemsReversed(typeBitSet(xClass, categories));
  }

  private final BitSet typeBitSet(Class<?> xClass, Class<?>... categories) {
    BitSet bitSet = new BitSet();
    for (Class<?> category : categories) {
      if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
        bitSet.or(subClassMap.get(category));
      }
    }
    return bitSet;
  }

  private final BitSet typeBitSet(Class<?> xClass, Collection<Class<?>> categories) {
    BitSet bitSet = new BitSet();
    for (Class<?> category : categories) {
      if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
        bitSet.or(subClassMap.get(category));
      }
    }
    return bitSet;
  }
}
