package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import org.jspecify.annotations.Nullable;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SubClassingBag<T> {
    final private ClassificationBag<Class<?>, T> items;
    final private HashMap<Class<?>, BitSet> subClassMap;

    public SubClassingBag(ClassificationBag<Class<?>, T> items, HashMap<Class<?>, List<Class<?>>> subClassMap) {
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

    public OrderedSet<T> getItems() {
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

    final public <X> ReversibleIterable<X> itemsOfType(Class<X> xClass, Class<?>... categories) {
        return items.getCategoryItems(xClass, typeBitSet(xClass, categories));
    }

    final public <X> ReversibleIterable<X> itemsOfType(Class<X> xClass, Collection<Class<?>> categories) {
        return items.getCategoryItems(xClass, typeBitSet(xClass, categories));
    }

    final public <X> ReversibleIterable<X> reversedItemsOfType(Class<X> xClass, Class<?>... categories) {
        return items.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
    }

    final public <X> ReversibleIterable<X> reversedItemsOfType(Class<X> xClass, Collection<Class<?>> categories) {
        return items.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
    }

    @SuppressWarnings("WeakerAccess")
    final public BitSet typeBitSet(Class<?> xClass, Class<?>... categories) {
        BitSet bitSet = new BitSet();
        for (Class<?> category : categories) {
            if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
                bitSet.or(subClassMap.get(category));
            }
        }
        return bitSet;
    }

    @SuppressWarnings("WeakerAccess")
    final public BitSet typeBitSet(Class<?> xClass, Collection<Class<?>> categories) {
        BitSet bitSet = new BitSet();
        for (Class<?> category : categories) {
            if (xClass.isAssignableFrom(category) && subClassMap.containsKey(category)) {
                bitSet.or(subClassMap.get(category));
            }
        }
        return bitSet;
    }
}
