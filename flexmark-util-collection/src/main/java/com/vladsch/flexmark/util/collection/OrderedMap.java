package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("WeakerAccess")
public class OrderedMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
    final @NotNull OrderedSet<K> keySet;
    final private @NotNull ArrayList<V> valueList;
    final private @Nullable CollectionHost<K> host;
    boolean inUpdate;
    private @Nullable Indexed<Entry<K, V>> indexedEntryProxy;
    private @Nullable Indexed<V> indexedValueProxy;

    public OrderedMap() {
        this(0, null);
    }

    public OrderedMap(int capacity) {
        this(capacity, null);
    }

    public OrderedMap(@NotNull CollectionHost<K> host) {
        this(0, host);
    }

    public OrderedMap(int capacity, @Nullable CollectionHost<K> host) {
        this.valueList = new ArrayList<>(capacity);
        this.host = host;
        this.indexedEntryProxy = null;
        this.indexedValueProxy = null;
        this.keySet = new OrderedSet<>(capacity, new CollectionHost<K>() {
            @Override
            public void adding(int index, @Nullable K k, @Nullable Object v) {
                OrderedMap.this.adding(index, k, v);
            }

            @Override
            public Object removing(int index, @Nullable K k) {
                return OrderedMap.this.removing(index, k);
            }

            @Override
            public void clearing() {
                OrderedMap.this.clearing();
            }

            @Override
            public void addingNulls(int index) {
                // can add anything, it will not be accessed, its a dummy holder
                OrderedMap.this.addingNull(index);
            }

            @Override
            public boolean skipHostUpdate() {
                return inUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMap.this.getModificationCount();
            }
        });
    }

    public @NotNull Indexed<Map.Entry<K, V>> getIndexedEntryProxy() {
        if (indexedEntryProxy != null) return indexedEntryProxy;
        indexedEntryProxy = new Indexed<Map.Entry<K, V>>() {
            @Override
            public Map.Entry<K, V> get(int index) {
                return OrderedMap.this.getEntry(index);
            }

            @Override
            public void set(int index, Map.Entry<K, V> item) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void removeAt(int index) {
                OrderedMap.this.keySet.removeIndexHosted(index);
            }

            @Override
            public int size() {
                return OrderedMap.this.size();
            }

            @Override
            public int modificationCount() {
                return OrderedMap.this.getModificationCount();
            }
        };

        return indexedEntryProxy;
    }

    public @NotNull Indexed<V> getIndexedValueProxy() {
        if (indexedValueProxy != null) return indexedValueProxy;
        indexedValueProxy = new Indexed<V>() {
            @Override
            public V get(int index) {
                return OrderedMap.this.getValue(index);
            }

            @Override
            public void set(int index, V item) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void removeAt(int index) {
                OrderedMap.this.keySet.removeIndexHosted(index);
            }

            @Override
            public int size() {
                return OrderedMap.this.size();
            }

            @Override
            public int modificationCount() {
                return OrderedMap.this.getModificationCount();
            }
        };

        return indexedValueProxy;
    }

    @NotNull Map.Entry<K, V> getEntry(int index) {
        return new MapEntry<>(keySet.getValue(index), valueList.get(index));
    }

    public int getModificationCount() {
        return keySet.getModificationCount();
    }

    void adding(int index, @NotNull K k, @NotNull Object v) {
        if (v == null) throw new IllegalArgumentException();
        if (host != null && !host.skipHostUpdate()) {
            host.adding(index, k, v);
        }
        //noinspection unchecked
        valueList.add((V) v);
    }

    void addingNull(int index) {
        if (host != null && !host.skipHostUpdate()) {
            host.addingNulls(index);
        }
        addNulls(index);
    }

    Object removing(int index, @NotNull K k) {
        if (host != null && !host.skipHostUpdate()) {
            host.removing(index, k);
        }
        return valueList.get(index);
    }

    void clearing() {
        if (host != null && !host.skipHostUpdate()) {
            host.clearing();
        }
        valueList.clear();
    }

    @Override
    public int size() {
        return keySet.size();
    }

    @Override
    public boolean isEmpty() {
        return keySet.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object o) {
        //noinspection SuspiciousMethodCalls
        return keySet.contains(o);
    }

    @Override
    public boolean containsValue(@Nullable Object o) {
        //noinspection SuspiciousMethodCalls
        int index = valueList.indexOf(o);
        return keySet.isValidIndex(index);
    }

    public void addNull() {
        addNulls(valueList.size());
    }

    public void addNulls(int index) {
        if (index < valueList.size())
            throw new IllegalArgumentException("addNulls(" + index + ") called when valueList size is " + valueList.size());
        while (valueList.size() <= index) valueList.add(null);
    }

    @Override
    public @Nullable V get(@Nullable Object o) {
        int index = keySet.indexOf(o);
        return index == -1 ? null : valueList.get(index);
    }

    @Override
    public @Nullable V put(@NotNull K k, @NotNull V v) {
        int index = keySet.indexOf(k);
        if (index == -1) {
            keySet.add(k, v);
            return null;
        }

        V old = valueList.get(index);
        valueList.set(index, v);
        return old;
    }

    public @NotNull V computeIfMissing(@NotNull K k, @NotNull Function<? super K, ? extends V> runnableValue) {
        int index = keySet.indexOf(k);
        if (index == -1) {
            V v = runnableValue.apply(k);
            keySet.add(k, v);
            return v;
        }

        return valueList.get(index);
    }

    @Override
    public @NotNull V remove(@Nullable Object o) {
        //noinspection unchecked
        return (V) keySet.removeHosted(o);
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void addAll(@NotNull Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        for (Map.Entry<? extends K, ? extends V> entry : entries) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        keySet.clear();
    }

    @NotNull
    @Override
    public OrderedSet<K> keySet() {
        return keySet;
    }

    @NotNull
    @Override
    public Collection<V> values() {
        if (!keySet.isSparse()) {
            return valueList;
        }

        ArrayList<V> values = new ArrayList<>(keySet.size());
        Iterator<Integer> iterator = keySet.indexIterator();
        while (iterator.hasNext()) {
            values.add(valueList.get(iterator.next()));
        }
        return values;
    }

    public @Nullable K getKey(int index) {
        if (!keySet.isValidIndex(index)) return null;
        return keySet.getValueList().get(index);
    }

    public @Nullable V getValue(int index) {
        if (!keySet.isValidIndex(index)) return null;
        return valueList.get(index);
    }

    @NotNull
    @Override
    public OrderedSet<Map.Entry<K, V>> entrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        inUpdate = true;
        OrderedSet<Map.Entry<K, V>> values = new OrderedSet<>(keySet.size(), new EntryCollectionHost<>());
        Iterator<Map.Entry<K, V>> iterator = entryIterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }

        // release it for host update
        inUpdate = false;

        return values;
    }

    public @NotNull List<Entry<K, V>> entries() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        List<Map.Entry<K, V>> values = new ArrayList<>();
        Iterator<Map.Entry<K, V>> iterator = entryIterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    public @NotNull List<K> keys() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        return keySet.values();
    }

    public @NotNull ReversibleIndexedIterator<V> valueIterator() {
        return new IndexedIterator<>(getIndexedValueProxy(), keySet.indexIterator());
    }

    public @NotNull ReversibleIndexedIterator<V> reversedValueIterator() {
        return new IndexedIterator<>(getIndexedValueProxy(), keySet.reversedIndexIterator());
    }

    public @NotNull ReversibleIndexedIterator<K> keyIterator() {
        return keySet.iterator();
    }

    public @NotNull ReversibleIndexedIterator<K> reversedKeyIterator() {
        return keySet.reversedIterator();
    }

    public @NotNull ReversibleIndexedIterator<Map.Entry<K, V>> entryIterator() {
        return new IndexedIterator<>(getIndexedEntryProxy(), keySet.indexIterator());
    }

    public @NotNull ReversibleIndexedIterator<Map.Entry<K, V>> reversedEntryIterator() {
        return new IndexedIterator<>(getIndexedEntryProxy(), keySet.reversedIndexIterator());
    }

    public @NotNull ReversibleIndexedIterator<Map.Entry<K, V>> reversedIterator() {
        return reversedEntryIterator();
    }

    public @NotNull ReversibleIterable<V> valueIterable() {
        return new IndexedIterable<>(getIndexedValueProxy(), keySet.indexIterable());
    }

    public @NotNull ReversibleIterable<V> reversedValueIterable() {
        return new IndexedIterable<>(getIndexedValueProxy(), keySet.reversedIndexIterable());
    }

    public @NotNull ReversibleIterable<K> keyIterable() {
        return keySet.iterable();
    }

    public @NotNull ReversibleIterable<K> reversedKeyIterable() {
        return keySet.reversedIterable();
    }

    public @NotNull ReversibleIterable<Map.Entry<K, V>> entryIterable() {
        return new IndexedIterable<>(getIndexedEntryProxy(), keySet.indexIterable());
    }

    public @NotNull ReversibleIterable<Entry<K, V>> reversedEntryIterable() {
        return new IndexedIterable<>(getIndexedEntryProxy(), keySet.reversedIndexIterable());
    }

    public @NotNull ReversibleIterable<Map.Entry<K, V>> reversedIterable() {
        return reversedEntryIterable();
    }

    /*
     * Iterable
     */

    @NotNull
    @Override
    public ReversibleIndexedIterator<Map.Entry<K, V>> iterator() {
        return entryIterator();
    }

    public void forEach(Consumer<? super Entry<K, V>> consumer) {
        Iterator<Map.Entry<K, V>> iterator = iterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedMap<?, ?> set = (OrderedMap<?, ?>) o;

        if (size() != set.size()) return false;
        return entrySet().equals(set.entrySet());
    }

    @Override
    public int hashCode() {
        int result = keySet.hashCode();
        result = 31 * result + valueList.hashCode();
        return result;
    }

    private class EntryCollectionHost<KK extends K, VV extends V> implements CollectionHost<Map.Entry<KK, VV>> {
        @Override
        public void adding(int index, @Nullable Entry<KK, VV> entry, @Nullable Object v) {
            assert v == null;
            OrderedMap.this.keySet.add(entry.getKey(), entry.getValue());
        }

        @Override
        public Object removing(int index, @Nullable Entry<KK, VV> entry) {
            OrderedMap.this.keySet.removeIndex(index);
            return entry;
        }

        @Override
        public void clearing() {
            OrderedMap.this.keySet.clear();
        }

        @Override
        public void addingNulls(int index) {
            OrderedMap.this.keySet.addNulls(index);
        }

        @Override
        public boolean skipHostUpdate() {
            return inUpdate;
        }

        @Override
        public int getIteratorModificationCount() {
            return OrderedMap.this.getModificationCount();
        }
    }
}
