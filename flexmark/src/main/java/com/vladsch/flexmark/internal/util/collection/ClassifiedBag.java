package com.vladsch.flexmark.internal.util.collection;

import java.util.Map;

public class ClassifiedBag<K, V> {
    private final OrderedSet<V> myItems;
    private final BitSetCollectingBag<K> myBag;
    private final Computable<K, V> myMapper;

    public ClassifiedBag(Computable<K, V> mapper) {
        this(0, mapper);
    }
    
    public ClassifiedBag(int capacity, Computable<K, V> mapper) {
        this.myItems = new OrderedSet<V>(capacity, new CollectionHost<V>() {
            @Override
            public void adding(int index, V v, Object v2) {
                K category = myMapper.compute(v);

                myBag.add(category, index);
            }

            @Override
            public Object removing(int index, V v) {
                K category = myMapper.compute(v);
                myBag.removeItem(category, index);
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

        this.myBag = new BitSetCollectingBag<K>();
        this.myMapper = mapper;
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
        CountingBitSet bitSet = myBag.get(category);
        return bitSet != null && bitSet.nextSetBit(0) != -1;
    }

    public CountingBitSet getCategorySet(K category) {
        return myBag.get(category);
    }

    public int getCategoryCount(K category) {
        CountingBitSet bitSet = myBag.get(category);
        return bitSet == null ? 0 : bitSet.count();
    }

    public Map<K, CountingBitSet> getCategoryMap() {
        return myBag;
    }

    public void clear() {
        myItems.clear();
    }
}
