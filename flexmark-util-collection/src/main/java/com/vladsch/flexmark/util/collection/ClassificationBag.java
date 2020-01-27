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
    final private @NotNull OrderedSet<V> items;
    final @NotNull IndexedItemBitSetMap<K, V> bag;
    final @Nullable CollectionHost<V> host;

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
        this.host = host;
        this.items = new OrderedSet<>(capacity, new CollectionHost<V>() {
            @Override
            public void adding(int index, @Nullable V v, @Nullable Object v2) {
                if (ClassificationBag.this.host != null && !ClassificationBag.this.host.skipHostUpdate()) ClassificationBag.this.host.adding(index, v, v2);
                if (v != null) bag.addItem(v, index);
            }

            @Override
            public Object removing(int index, @Nullable V v) {
                if (ClassificationBag.this.host != null && !ClassificationBag.this.host.skipHostUpdate()) ClassificationBag.this.host.removing(index, v);
                if (v != null) bag.removeItem(v, index);
                return null;
            }

            @Override
            public void clearing() {
                if (ClassificationBag.this.host != null && !ClassificationBag.this.host.skipHostUpdate()) ClassificationBag.this.host.clearing();
                bag.clear();
            }

            @Override
            public void addingNulls(int index) {
                // nothing to be done, we're good
                if (ClassificationBag.this.host != null && !ClassificationBag.this.host.skipHostUpdate()) ClassificationBag.this.host.addingNulls(index);
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

        this.bag = new IndexedItemBitSetMap<>(mapper);
    }

    @NotNull
    public OrderedSet<V> getItems() {
        return items;
    }

    @SuppressWarnings("WeakerAccess")
    public int getModificationCount() {
        return items.getModificationCount();
    }

    public boolean add(@Nullable V item) {
        return items.add(item);
    }

    public boolean remove(@Nullable V item) {
        return items.remove(item);
    }

    public boolean remove(int index) {
        return items.removeIndex(index);
    }

    public boolean contains(@Nullable V item) {
        return items.contains(item);
    }

    public boolean containsCategory(@Nullable K category) {
        BitSet bitSet = bag.get(category);
        return bitSet != null && !bitSet.isEmpty();
    }

    public @Nullable BitSet getCategorySet(@Nullable K category) {
        return bag.get(category);
    }

    @SuppressWarnings("WeakerAccess")
    public int getCategoryCount(@Nullable K category) {
        BitSet bitSet = bag.get(category);
        return bitSet == null ? 0 : bitSet.cardinality();
    }

    public @NotNull Map<K, BitSet> getCategoryMap() {
        return bag;
    }

    public void clear() {
        items.clear();
    }

    @SafeVarargs
    final public <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> xClass, @NotNull K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(items.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    final public <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> xClass, @NotNull Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(items.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), false));
    }

    final public <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> xClass, @NotNull BitSet bitSet) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(items.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, false));
    }

    @SafeVarargs
    final public <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(@NotNull Class<? extends X> xClass, @NotNull K... categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(items.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    final public <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(@NotNull Class<? extends X> xClass, @NotNull Collection<? extends K> categories) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(items.getConcurrentModsIndexedProxy(), new BitSetIterable(categoriesBitSet(categories), true));
    }

    final public <X> @NotNull ReversibleIterable<X> getCategoryItemsReversed(@NotNull Class<? extends X> xClass, @NotNull BitSet bitSet) {
        return new IndexedIterable<X, V, ReversibleIterable<Integer>>(items.getConcurrentModsIndexedProxy(), new BitSetIterable(bitSet, true));
    }

    @SafeVarargs
    @SuppressWarnings("WeakerAccess")
    final public @NotNull BitSet categoriesBitSet(@NotNull K... categories) {
        BitSet bitSet = new BitSet();
        for (K category : categories) {
            BitSet bitSet1 = bag.get(category);
            if (bitSet1 != null) {
                bitSet.or(bitSet1);
            }
        }
        return bitSet;
    }

    final public @NotNull BitSet categoriesBitSet(@NotNull Collection<? extends K> categories) {
        BitSet bitSet = new BitSet();
        for (K category : categories) {
            BitSet bitSet1 = bag.get(category);
            if (bitSet1 != null) {
                bitSet.or(bitSet1);
            }
        }
        return bitSet;
    }
}
