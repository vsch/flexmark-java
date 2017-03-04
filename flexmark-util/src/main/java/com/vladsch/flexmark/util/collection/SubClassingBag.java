package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SubClassingBag<T> {
    private final ClassificationBag<Class, T> myItems;
    private final HashMap<Class, BitSet> mySubClassMap;

    public SubClassingBag(final ClassificationBag<Class, T> items, final HashMap<Class, List<Class>> subClassMap) {
        myItems = items;
        mySubClassMap = new HashMap<Class, BitSet>();

        for (Class clazz : subClassMap.keySet()) {
            List<Class> classList = subClassMap.get(clazz);
            BitSet bitSet = myItems.categoriesBitSet(classList);
            if (!bitSet.isEmpty()) {
                mySubClassMap.put(clazz, bitSet);
            }
        }
    }

    public OrderedSet<T> getItems() {
        return myItems.getItems();
    }

    public boolean contains(final T item) {
        return myItems.contains(item);
    }

    public boolean containsType(final Class type) {
        return myItems.containsCategory(type);
    }

    public BitSet getTypeSet(final Class category) {
        return mySubClassMap.get(category);
    }

    public int getTypeCount(final Class category) {
        BitSet bitSet = mySubClassMap.get(category);
        return bitSet == null ? 0 : bitSet.cardinality();
    }

    public final <X> ReversibleIterable<X> itemsOfType(Class<X> xClass, Class... categories) {
        return myItems.getCategoryItems(xClass, typeBitSet(xClass, categories));
    }

    public final <X> ReversibleIterable<X> itemsOfType(Class<X> xClass, Collection<Class<?>> categories) {
        return myItems.getCategoryItems(xClass, typeBitSet(xClass, categories));
    }

    public final <X> ReversibleIterable<X> reversedItemsOfType(Class<X> xClass, Class... categories) {
        return myItems.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
    }

    public final <X> ReversibleIterable<X> reversedItemsOfType(Class<X> xClass, Collection<Class<?>> categories) {
        return myItems.getCategoryItemsReversed(xClass, typeBitSet(xClass, categories));
    }

    @SuppressWarnings("WeakerAccess")
    public final BitSet typeBitSet(Class<?> xClass, Class... categories) {
        BitSet bitSet = new BitSet();
        for (Class<?> category : categories) {
            if (xClass.isAssignableFrom(category) && mySubClassMap.containsKey(category)) {
                bitSet.or(mySubClassMap.get(category));
            }
        }
        return bitSet;
    }

    @SuppressWarnings("WeakerAccess")
    public final BitSet typeBitSet(Class<?> xClass, Collection<Class<?>> categories) {
        BitSet bitSet = new BitSet();
        for (Class<?> category : categories) {
            if (xClass.isAssignableFrom(category) && mySubClassMap.containsKey(category)) {
                bitSet.or(mySubClassMap.get(category));
            }
        }
        return bitSet;
    }
}
