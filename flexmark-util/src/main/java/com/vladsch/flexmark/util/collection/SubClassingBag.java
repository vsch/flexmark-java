package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SubClassingBag<T> {
    private final @NotNull ClassificationBag<Class<?>, T> myItems;
    private final @NotNull HashMap<Class<?>, BitSet> mySubClassMap;

    public SubClassingBag(@NotNull ClassificationBag<Class<?>, T> items, HashMap<Class<?>, @NotNull List<Class<?>>> subClassMap) {
        myItems = items;
        mySubClassMap = new HashMap<>();

        for (Class<?> clazz : subClassMap.keySet()) {
            List<Class<?>> classList = subClassMap.get(clazz);
            BitSet bitSet = myItems.categoriesBitSet(classList);
            if (!bitSet.isEmpty()) {
                mySubClassMap.put(clazz, bitSet);
            }
        }
    }

    public @NotNull OrderedSet<T> getItems() {
        return myItems.getItems();
    }

    public boolean contains(@Nullable T item) {
        return myItems.contains(item);
    }

    public boolean containsType(@Nullable Class<?> type) {
        return myItems.containsCategory(type);
    }

    public BitSet getTypeSet(@Nullable Class<?> category) {
        return mySubClassMap.get(category);
    }

    public int getTypeCount(@Nullable Class<?> category) {
        BitSet bitSet = mySubClassMap.get(category);
        return bitSet == null ? 0 : bitSet.cardinality();
    }

    public final <X> @NotNull ReversibleIterable<X> itemsOfType(@NotNull Class<X> xClass, @NotNull Class<?>... categories) {
        return myItems.getCategoryItems(xClass, typeBitSet(xClass, categories));
    }

    public final <X> @NotNull ReversibleIterable<X> itemsOfType(@NotNull Class<X> xClass, @NotNull Collection<Class<?>> categories) {
        return myItems.getCategoryItems(xClass, typeBitSet(xClass, categories));
    }

    public final <X> @NotNull ReversibleIterable<X> reversedItemsOfType(@NotNull Class<X> xClass, @NotNull Class<?>... categories) {
        return myItems.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
    }

    public final <X> @NotNull ReversibleIterable<X> reversedItemsOfType(@NotNull Class<X> xClass, @NotNull Collection<Class<?>> categories) {
        return myItems.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
    }

    @SuppressWarnings("WeakerAccess")
    public final @NotNull BitSet typeBitSet(@NotNull Class<?> xClass, @NotNull Class<?>... categories) {
        BitSet bitSet = new BitSet();
        for (Class<?> category : categories) {
            if (xClass.isAssignableFrom(category) && mySubClassMap.containsKey(category)) {
                bitSet.or(mySubClassMap.get(category));
            }
        }
        return bitSet;
    }

    @SuppressWarnings("WeakerAccess")
    public final @NotNull BitSet typeBitSet(@NotNull Class<?> xClass, @NotNull Collection<Class<?>> categories) {
        BitSet bitSet = new BitSet();
        for (Class<?> category : categories) {
            if (xClass.isAssignableFrom(category) && mySubClassMap.containsKey(category)) {
                bitSet.or(mySubClassMap.get(category));
            }
        }
        return bitSet;
    }
}
