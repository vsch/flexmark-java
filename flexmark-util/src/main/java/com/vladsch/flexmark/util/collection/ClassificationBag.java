package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.BitSetIterable;
import com.vladsch.flexmark.util.collection.iteration.IndexedIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class ClassificationBag<K, V> {
    private final @NotNull OrderedSet<V> myItems;
    final @NotNull IndexedItemBitSetMap<K, V> myBag;
    final @Nullable CollectionHost<V> myHost;

    public ClassificationBag(Function<V, K> mapper) {
        this(0, mapper);
    }

    public ClassificationBag(@NotNull Function<V, K> mapper, @Nullable CollectionHost<V> host) {
        this(0, mapper, host);
    }

    public ClassificationBag(int capacity, @NotNull Function<V, K> mapper) {
        this(capacity, mapper, null);
    }

    public ClassificationBag(int capacity, @NotNull Function<V, K> mapper, @Nullable CollectionHost<V> host) {
        this.myHost = host;
        this.myItems = new OrderedSet<>(capacity, new CollectionHost<V>() {
            @Override
            public void adding(int index, @Nullable V v, @Nullable Object v2) {
                if (myHost != null && !myHost.skipHostUpdate()) myHost.adding(index, v, v2);
                myBag.addItem(v, index);
            }

            @Override
            public Object removing(int index, @Nullable V v) {
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

        this.myBag = new IndexedItemBitSetMap<>(mapper);
    }

    @NotNull
    public OrderedSet<V> getItems() {
        return myItems;
    }

    @SuppressWarnings("WeakerAccess")
    public int getModificationCount() {
        return myItems.getModificationCount();
    }

    public boolean add(@Nullable V item) {
        return myItems.add(item);
    }

    public boolean remove(@Nullable V item) {
        return myItems.remove(item);
    }

    public boolean remove(int index) {
        return myItems.removeIndex(index);
    }

    public boolean contains(@Nullable V item) {
        return myItems.contains(item);
    }

    public boolean containsCategory(@Nullable K category) {
        BitSet bitSet = myBag.get(category);
        return bitSet != null && !bitSet.isEmpty();
    }

    public @Nullable BitSet getCategorySet(@Nullable K category) {
        return myBag.get(category);
    }

    @SuppressWarnings("WeakerAccess")
    public int getCategoryCount(@Nullable K category) {
        BitSet bitSet = myBag.get(category);
        return bitSet == null ? 0 : bitSet.cardinality();
    }

    public @NotNull Map<K, BitSet> getCategoryMap() {
        return myBag;
    }

    public void clear() {
        myItems.clear();
    }

    @SafeVarargs
    public final <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> xClass, @NotNull K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    public final <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> xClass, @NotNull Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    public final <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> xClass, @NotNull BitSet bitSet) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, false));
    }

    @SafeVarargs
    public final <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(@NotNull Class<? extends X> xClass, @NotNull K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    public final <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(@NotNull Class<? extends X> xClass, @NotNull Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    public final <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(@NotNull Class<? extends X> xClass, @NotNull BitSet bitSet) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(myItems.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, true));
    }

    @SafeVarargs
    @SuppressWarnings("WeakerAccess")
    public final @NotNull BitSet categoriesBitSet(@NotNull K... categories) {
        BitSet bitSet = new BitSet();
        for (K category : categories) {
            BitSet bitSet1 = myBag.get(category);
            if (bitSet1 != null) {
                bitSet.or(bitSet1);
            }
        }
        return bitSet;
    }

    public final @NotNull BitSet categoriesBitSet(@NotNull Collection<? extends K> categories) {
        BitSet bitSet = new BitSet();
        for (K category : categories) {
            BitSet bitSet1 = myBag.get(category);
            if (bitSet1 != null) {
                bitSet.or(bitSet1);
            }
        }
        return bitSet;
    }
}
