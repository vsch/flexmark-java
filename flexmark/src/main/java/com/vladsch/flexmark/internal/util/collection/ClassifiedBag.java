package com.vladsch.flexmark.internal.util.collection;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;
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

    @SafeVarargs
    public final <X extends V> ReversibleIterable<X> getCategoryItems(Class<X> xClass, K... categories) {
        return new ItemIterable<>(myItems.getValueList(), categoriesBitSet(categories), false);
    }
    
    public final <X extends V> ReversibleIterable<X> getCategoryItems(Class<X> xClass, Collection<? extends K> categories) {
        return new ItemIterable<>(myItems.getValueList(), categoriesBitSet(categories), false);
    }
    
    @SafeVarargs
    public final <X extends V> ReversibleIterable<X> getCategoryItemsReversed(Class<X> xClass, K... categories) {
        return new ItemIterable<>(myItems.getValueList(), categoriesBitSet(categories), true);
    }

    public final <X extends V> ReversibleIterable<X> getCategoryItemsReversed(Class<X> xClass, Collection<? extends K> categories) {
        return new ItemIterable<>(myItems.getValueList(), categoriesBitSet(categories), true);
    }

    @SafeVarargs
    public final CountingBitSet categoriesBitSet(K... categories) {
        CountingBitSet bitSet = new CountingBitSet();
        for (K category : categories) {
            if (containsCategory(category)) {
                bitSet.or(myBag.get(category));
            }
        }
        return bitSet;
    }

    public final CountingBitSet categoriesBitSet(Collection<? extends K> categories) {
        CountingBitSet bitSet = new CountingBitSet();
        for (K category : categories) {
            if (containsCategory(category)) {
                bitSet.or(myBag.get(category));
            }
        }
        return bitSet;
    }

    public static class ItemIterable<X, Y> implements ReversibleIterable<X> {
        final private List<Y> items;
        final private BitSet bitSet;
        final private boolean reversed;

        public ItemIterable(List<Y> items, BitSet bitSet, boolean reversed) {
            this.items = items;
            this.bitSet = bitSet;
            this.reversed = reversed;
        }

        @Override
        public ReversibleIterator<X> iterator() {
            return new ItemIterator<X, Y>(items, bitSet, reversed);
        }

        @Override
        public ReversibleIterable<X> reversed() {
            return new ItemIterable<X,Y>(items, bitSet, !reversed);
        }

        @Override
        public boolean isReversed() {
            return reversed;
        }

        @Override
        public ReversibleIterator<X> reversedIterator() {
            return new ItemIterator<X,Y>(items, bitSet, !reversed);
        }
    }

    public static class ItemIterator<X,Y> extends AbstractBitSetIterator<X> {
        final private List<Y> items;

        public ItemIterator(List<Y> items, BitSet bitSet, boolean reversed) {
            super(bitSet, true, reversed);
            this.items = items;
        }

        @Override
        protected int maxSize() {
            return items.size();
        }

        @Override
        protected void remove(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SparseIterator<X> reversed() {
            return new ItemIterator<X,Y>(items, bitSet, !isReversed());
        }

        @Override
        protected X getValueAt(int index) {
            return (X)items.get(index);
        }

        @Override
        protected void checkConcurrentMods() {

        }
    }
}
