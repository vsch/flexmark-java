package com.vladsch.flexmark.internal.util.collection;

import java.util.BitSet;
import java.util.Collection;
import java.util.Map;

public class ClassificationBag<K, V> {
    private final OrderedSet<V> myItems;
    private final IndexedItemBitSetMap<K, V> myBag;

    public ClassificationBag(Computable<K, V> mapper) {
        this(0, mapper);
    }

    public ClassificationBag(int capacity, Computable<K, V> mapper) {
        this.myItems = new OrderedSet<V>(capacity, new CollectionHost<V>() {
            @Override
            public void adding(int index, V v, Object v2) {
                myBag.addItem(v, index);
            }

            @Override
            public Object removing(int index, V v) {
                myBag.removeItem(v, index);
                return null;
            }

            @Override
            public void clearing() {
                myBag.clear();
            }

            @Override
            public void addingNulls(int index) {
                // nothing to be done, we're good
            }

            @Override
            public boolean skipHostUpdate() {
                return false;
            }

            @Override
            public int getIteratorModificationCount() {
                return myItems.getModificationCount();
            }
        });

        this.myBag = new IndexedItemBitSetMap<K, V>(mapper);
    }

    public boolean add(V item) {
        return myItems.add(item);
    }

    public boolean remove(V item) {
        return myItems.remove(item);
    }

    public boolean remove(int index) {
        return myItems.removeIndex(index);
    }

    public boolean contains(V item) {
        return myItems.contains(item);
    }

    public boolean containsCategory(K category) {
        BitSet bitSet = myBag.get(category);
        return bitSet != null && !bitSet.isEmpty();
    }

    public BitSet getCategorySet(K category) {
        return myBag.get(category);
    }

    public int getCategoryCount(K category) {
        BitSet bitSet = myBag.get(category);
        return bitSet == null ? 0 : bitSet.cardinality();
    }

    public Map<K, BitSet> getCategoryMap() {
        return myBag;
    }

    public void clear() {
        myItems.clear();
    }

    @SafeVarargs
    public final <X extends V> ReversibleIterable<X> getCategoryItems(Class<X> xClass, K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    public final <X extends V> ReversibleIterable<X> getCategoryItems(Class<X> xClass, Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    @SafeVarargs
    public final <X extends V> ReversibleIterable<X> getCategoryItemsReversed(Class<X> xClass, K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    public final <X extends V> ReversibleIterable<X> getCategoryItemsReversed(Class<X> xClass, Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    @SafeVarargs
    public final BitSet categoriesBitSet(K... categories) {
        BitSet bitSet = new BitSet();
        for (K category : categories) {
            if (containsCategory(category)) {
                bitSet.or(myBag.get(category));
            }
        }
        return bitSet;
    }

    public final BitSet categoriesBitSet(Collection<? extends K> categories) {
        BitSet bitSet = new BitSet();
        for (K category : categories) {
            if (containsCategory(category)) {
                bitSet.or(myBag.get(category));
            }
        }
        return bitSet;
    }
}
