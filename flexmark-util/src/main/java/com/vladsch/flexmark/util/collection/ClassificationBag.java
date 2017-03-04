package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.Computable;
import com.vladsch.flexmark.util.collection.iteration.BitSetIterable;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;

import java.util.BitSet;
import java.util.Collection;
import java.util.Map;

public class ClassificationBag<K, V> {
    private final OrderedSet<V> myItems;
    private final IndexedItemBitSetMap<K, V> myBag;
    private final CollectionHost<V> myHost;

    public ClassificationBag(Computable<K, V> mapper) {
        this(0, mapper);
    }

    public ClassificationBag(Computable<K, V> mapper, CollectionHost<V> host) {
        this(0, mapper, host);
    }

    public ClassificationBag(int capacity, Computable<K, V> mapper) {
        this(capacity, mapper, null);
    }

    public ClassificationBag(int capacity, Computable<K, V> mapper, CollectionHost<V> host) {
        this.myHost = host;
        this.myItems = new OrderedSet<V>(capacity, new CollectionHost<V>() {
            @Override
            public void adding(int index, V v, Object v2) {
                if (myHost != null && !myHost.skipHostUpdate()) myHost.adding(index, v, v2);
                myBag.addItem(v, index);
            }

            @Override
            public Object removing(int index, V v) {
                if (myHost != null && !myHost.skipHostUpdate()) myHost.removing(index, v);
                myBag.removeItem(v, index);
                return null;
            }

            @Override
            public void clearing() {
                if (myHost != null && !myHost.skipHostUpdate()) myHost.clearing();
                myBag.clear();
            }

            @Override
            public void addingNulls(int index) {
                // nothing to be done, we're good
                if (myHost != null && !myHost.skipHostUpdate()) myHost.addingNulls(index);
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

        this.myBag = new IndexedItemBitSetMap<K, V>(mapper);
    }

    public OrderedSet<V> getItems() {
        return myItems;
    }

    @SuppressWarnings("WeakerAccess")
    public int getModificationCount() {
        return myItems.getModificationCount();
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

    @SuppressWarnings("WeakerAccess")
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

    public final <X> ReversibleIterable<X> getCategoryItems(Class<? extends X> xClass, K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    public final <X> ReversibleIterable<X> getCategoryItems(Class<? extends X> xClass, Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    public final <X> ReversibleIterable<X> getCategoryItems(Class<? extends X> xClass, BitSet bitSet) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, false));
    }

    public final <X> ReversibleIterable<X> getCategoryItemsReversed(Class<? extends X> xClass, K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    public final <X> ReversibleIterable<X> getCategoryItemsReversed(Class<? extends X> xClass, Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    public final <X> ReversibleIterable<X> getCategoryItemsReversed(Class<? extends X> xClass, BitSet bitSet) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, true));
    }

    @SuppressWarnings("WeakerAccess")
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
