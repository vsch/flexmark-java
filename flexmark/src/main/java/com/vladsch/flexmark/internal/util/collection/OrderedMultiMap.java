package com.vladsch.flexmark.internal.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class OrderedMultiMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
    final private OrderedSet<K> keySet;
    final private OrderedSet<V> valueSet;
    final private CollectionHost<Paired<K, V>> host;
    private boolean inKeyUpdate;
    private boolean inValueUpdate;

    public OrderedMultiMap() {
        this(0, null);
    }

    public OrderedMultiMap(int capacity) {
        this(capacity, null);
    }

    public OrderedMultiMap(CollectionHost<Paired<K, V>> host) {
        this(0, host);
    }

    public OrderedMultiMap(int capacity, CollectionHost<Paired<K, V>> host) {
        this.host = host;
        this.valueSet = new OrderedSet<V>(capacity, new CollectionHost<V>() {
            @Override
            public void adding(int index, V v, Object k) {
                addingValue(index, v, k);
            }

            @Override
            public Object removing(int index, V v) {
                return removingValue(index, v);
            }

            @Override
            public void clearing() {
                clear();
            }

            @Override
            public void addingNulls(int index) {
                addingNullValue(index);
            }

            @Override
            public boolean skipHostUpdate() {
                return inKeyUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        });

        this.keySet = new OrderedSet<K>(capacity, new CollectionHost<K>() {
            @Override
            public void adding(int index, K k, Object v) {
                addingKey(index, k, v);
            }

            @Override
            public Object removing(int index, K k) {
                return removingKey(index, k);
            }

            @Override
            public void clearing() {
                clear();
            }

            @Override
            public void addingNulls(int index) {
                addingNullKey(index);
            }

            @Override
            public boolean skipHostUpdate() {
                return inValueUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        });
    }

    public int getModificationCount() {
        return (int) ((long) keySet.getModificationCount() + (long) valueSet.getModificationCount());
    }

    private void addingKey(int index, K k, Object v) {
        assert !inValueUpdate;

        inValueUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            host.adding(index, new Pair<>(k, (V) v), null);
        }
        if (v == null) valueSet.addNulls(index);
        else valueSet.add((V) v);
        inValueUpdate = false;
    }

    private void addingNullKey(int index) {
        assert !inValueUpdate;

        inValueUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            host.addingNulls(index);
        }
        while (valueSet().size() <= index) valueSet.add(null);
        inValueUpdate = false;
    }

    private Object removingKey(int index, K k) {
        assert !inValueUpdate;

        inValueUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            host.removing(index, new Pair<>(k, null));
        }
        Object r = valueSet.removeIndexHosted(index);
        inValueUpdate = false;
        return r;
    }

    private void addingValue(int index, V v, Object k) {
        assert !inKeyUpdate;

        inKeyUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            host.adding(index, new Pair<>((K) k, v), null);
        }
        if (k == null) keySet.addNulls(index);
        else keySet.add((K) k);
        inKeyUpdate = false;
    }

    private void addingNullValue(int index) {
        assert !inKeyUpdate;

        inKeyUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            host.addingNulls(index);
        }
        while (keySet.size() <= index) keySet.add(null);
        inKeyUpdate = false;
    }

    private Object removingValue(int index, V v) {
        assert !inKeyUpdate;

        inKeyUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            host.removing(index, new Pair<>(null, v));
        }
        Object r = keySet.removeIndexHosted(index);
        inKeyUpdate = false;
        return r;
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
    public boolean containsKey(Object o) {
        return keySet.contains(o);
    }

    @Override
    public boolean containsValue(Object o) {
        int index = valueSet.indexOf(o);
        return keySet.isValidIndex(index);
    }

    @Override
    public V get(Object o) {
        return getKeyValue(o);
    }

    public V getKeyValue(Object o) {
        int index = keySet.indexOf(o);
        return index == -1 ? null : valueSet.getValue(index);
    }

    public K getValueKey(Object o) {
        int index = valueSet.indexOf(o);
        return index == -1 ? null : keySet.getValue(index);
    }

    @Override
    public V put(K k, V v) {
        return putKeyValue(k, v);
    }

    public void addNullEntry(int index) {
        inKeyUpdate = true;
        inValueUpdate = true;

        if (host != null && !host.skipHostUpdate()) {
            host.addingNulls(index);
        }
        keySet.addNulls(index);
        valueSet.addNulls(index);

        inValueUpdate = false;
        inKeyUpdate = false;
    }

    public boolean putEntry(Map.Entry<K, V> e) {
        return addKeyValue(e.getKey(), e.getValue());
    }

    public boolean putKeyValueEntry(Map.Entry<K, V> e) {
        return addKeyValue(e.getKey(), e.getValue());
    }

    public boolean putValueKeyEntry(Map.Entry<V, K> e) {
        return addKeyValue(e.getValue(), e.getKey());
    }

    public boolean putKeyValuePair(Paired<K, V> e) {
        return addKeyValue(e.getFirst(), e.getSecond());
    }

    public boolean putValueKeyPair(Paired<V, K> e) {
        return addKeyValue(e.getSecond(), e.getFirst());
    }

    public V putKeyValue(K k, V v) {
        return !addKeyValue(k, v) ? v : null;
    }

    public K putValueKey(V v, K k) {
        return !addKeyValue(k, v) ? k : null;
    }

    private boolean addKeyValue(K k, V v) {
        int keyIndex = keySet.indexOf(k);
        int valueIndex = valueSet.indexOf(v);

        if (keyIndex == -1 && valueIndex == -1) {
            // neither one exists/ we add both
            inKeyUpdate = true;
            inValueUpdate = true;
            if (host != null && !host.skipHostUpdate()) {
                host.adding(keySet.getValueList().size(), new Pair<>(k, v), null);
            }
            keySet.add(k, v);
            valueSet.add(v, k);
            inValueUpdate = false;
            inKeyUpdate = false;

            return true;
        }

        if (keyIndex == -1) {
            inKeyUpdate = true;
            inValueUpdate = true;
            if (host != null && !host.skipHostUpdate()) {
                host.adding(valueIndex, new Pair<>(k, v), null);
            }
            keySet.setValueAt(valueIndex, k, v);
            inValueUpdate = false;
            inKeyUpdate = false;
            return true;
        }

        if (valueIndex == -1) {
            inKeyUpdate = true;
            inValueUpdate = true;
            if (host != null && !host.skipHostUpdate()) {
                host.adding(keyIndex, new Pair<>(k, v), null);
            }
            valueSet.setValueAt(keyIndex, v, k);
            inValueUpdate = false;
            inValueUpdate = false;
            return true;
        }

        if (valueIndex != keyIndex) {
            throw new IllegalStateException("keySet[" + keyIndex + "]=" + k + " and valueSet[" + valueIndex + "]=" + v + " are out of sync");
        }

        return false;
    }

    @Override
    public V remove(Object o) {
        return removeKey(o);
    }

    public Map.Entry<K, V> removeEntry(Map.Entry<K, V> e) {
        boolean b = removeEntryIndex(-1, e.getKey(), e.getValue());
        return b ? e : null;
    }

    private boolean removeEntryIndex(int index, K k, V v) {
        int keyIndex = keySet.indexOf(k);
        int valueIndex = valueSet.indexOf(v);

        if (keyIndex != valueIndex) {
            throw new IllegalStateException("keySet[" + keyIndex + "]=" + k + " and valueSet[" + valueIndex + "]=" + v + " are out of sync");
        }

        if (index != -1 && keyIndex != index) {
            throw new IllegalStateException("removeEntryIndex " + index + " does not match keySet[" + keyIndex + "]=" + k + " and valueSet[" + valueIndex + "]=" + v + " are out of sync");
        }

        if (keyIndex != -1) {
            inKeyUpdate = true;
            inValueUpdate = true;
            if (host != null && !host.skipHostUpdate()) {
                host.removing(keyIndex, new Pair<>(k, v));
            }
            keySet.removeHosted(k);
            valueSet.removeHosted(v);
            inValueUpdate = false;
            inKeyUpdate = false;
            return true;
        }
        return false;
    }

    public V removeKey(Object o) {
        inKeyUpdate = true;
        if (host != null && !host.skipHostUpdate()) {
            int index = keySet.indexOf(o);
            if (index != -1) {
                host.removing(index, new Pair<>((K) o, valueSet.isValidIndex(index) ? valueSet.getValue(index) : null));
            }
        }
        V r = (V) keySet.removeHosted(o);
        inKeyUpdate = false;
        return r;
    }

    public K removeValue(Object o) {
        inValueUpdate = true;
        int index = valueSet.indexOf(o);
        if (host != null && !host.skipHostUpdate()) {
            if (index != -1) {
                host.removing(index, new Pair<>(keySet.isValidIndex(index) ? keySet.getValue(index) : null, (V) o));
            }
        }
        K r = (K) valueSet.removeHosted(o);
        inValueUpdate = false;
        return r;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        putAllKeyValues(map);
    }

    public void putAllKeyValues(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void putAllValueKeys(Map<? extends V, ? extends K> map) {
        for (Map.Entry<? extends V, ? extends K> entry : map.entrySet()) {
            putValueKey(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        inValueUpdate = true;
        inKeyUpdate = true;

        if (host != null && !host.skipHostUpdate()) {
            host.clearing();
        }
        keySet.clear();
        valueSet.clear();

        inKeyUpdate = false;
        inValueUpdate = false;
    }

    @Override
    public OrderedSet<K> keySet() {
        return keySet;
    }

    @Override
    public Collection<V> values() {
        if (!keySet.isSparse()) {
            return valueSet;
        }

        ArrayList<V> values = new ArrayList<V>(keySet.size());
        SparseIterator<V> iterator = valueSet.iterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    public OrderedSet<V> valueSet() {
        return valueSet;
    }

    public Collection<K> keys() {
        if (!keySet.isSparse()) {
            return keySet;
        }

        ArrayList<K> values = new ArrayList<K>(valueSet.size());
        SparseIterator<K> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    public K getKey(int index) {
        if (!keySet.isValidIndex(index)) return null;
        return keySet.getValueList().get(index);
    }

    public V getValue(int index) {
        if (!valueSet.isValidIndex(index)) return null;
        return valueSet.getValue(index);
    }

    @Override
    public OrderedSet<Map.Entry<K, V>> entrySet() {
        return keyValueEntrySet();
    }

    public ReversibleIterator<V> valueIterator() {
        return new ValueIterator<K, V>(this);
    }

    public ReversibleIterator<V> reversedValueIterator() {
        return new ValueIterator<K, V>(this, true);
    }

    public ReversibleIterator<K> keyIterator() {
        return new KeyIterator<K, V>(this);
    }

    public ReversibleIterator<K> reversedKeyIterator() {
        return new KeyIterator<K, V>(this, true);
    }

    public ReversibleIterator<Map.Entry<K, V>> entryIterator() {
        return keyValueEntryIterator();
    }

    public ReversibleIterator<Map.Entry<K, V>> reversedEntryIterator() {
        return reversedKeyValueEntryIterator();
    }

    public ReversibleIterator<Map.Entry<K, V>> keyValueEntryIterator() {
        return new KeyValueEntryIterator<K, V>(this);
    }

    public ReversibleIterator<Map.Entry<K, V>> reversedKeyValueEntryIterator() {
        return new KeyValueEntryIterator<K, V>(this, true);
    }

    public ReversibleIterator<ValueKeyEntry<K, V>> valueKeyEntryIterator() {
        return new ValueKeyEntryIterator<K, V>(this);
    }

    public ReversibleIterator<ValueKeyEntry<K, V>> reversedValueKeyEntryIterator() {
        return new ValueKeyEntryIterator<K, V>(this, true);
    }

    /*
     * Iterable
     */

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return entryIterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
        Iterator<Map.Entry<K, V>> iterator = entryIterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

    public OrderedSet<Map.Entry<K, V>> keyValueEntrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        inValueUpdate = true;
        inKeyUpdate = true;

        OrderedSet<Map.Entry<K, V>> values = new OrderedSet<>(keySet.size(), new CollectionHost<Map.Entry<K, V>>() {
            @Override
            public void adding(int index, Map.Entry<K, V> entry, Object v) {
                assert v == null;
                OrderedMultiMap.this.putKeyValue(entry.getKey(), entry.getValue());
            }

            @Override
            public Object removing(int index, Map.Entry<K, V> entry) {
                boolean b = OrderedMultiMap.this.removeEntryIndex(index, entry.getKey(), entry.getValue());
                return b ? entry : null;
            }

            @Override
            public void clearing() {
                OrderedMultiMap.this.clear();
            }

            @Override
            public void addingNulls(int index) {
                OrderedMultiMap.this.addNullEntry(index);
            }

            @Override
            public boolean skipHostUpdate() {
                return inKeyUpdate || inValueUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        });

        SparseIterator<Integer> iterator = keySet.indexIterator();
        while (iterator.hasNext()) {
            iterator.next();
            values.add(new Entry<K, V>(this, iterator.getIndex()));
        }

        // release it for host update
        inValueUpdate = false;
        inKeyUpdate = false;

        return values;
    }

    public OrderedSet<Map.Entry<V, K>> valueKeyEntrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        inValueUpdate = true;
        inKeyUpdate = true;
        OrderedSet<Map.Entry<V, K>> values = new OrderedSet<>(valueSet.size(), new CollectionHost<Map.Entry<V, K>>() {
            @Override
            public void adding(int index, Map.Entry<V, K> entry, Object k) {
                assert k == null;
                OrderedMultiMap.this.putKeyValue(entry.getValue(), entry.getKey());
            }

            @Override
            public Object removing(int index, Map.Entry<V, K> entry) {
                boolean b = OrderedMultiMap.this.removeEntryIndex(index, entry.getValue(), entry.getKey());
                return b ? entry : null;
            }

            @Override
            public void clearing() {
                OrderedMultiMap.this.clear();
            }

            @Override
            public void addingNulls(int index) {
                OrderedMultiMap.this.addNullEntry(index);
            }

            @Override
            public boolean skipHostUpdate() {
                return inKeyUpdate || inValueUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        });

        SparseIterator<Integer> iterator = keySet.indexIterator();
        while (iterator.hasNext()) {
            iterator.next();
            values.add(new ValueKeyEntry<K, V>(this, iterator.getIndex()));
        }

        // release it for host update
        inValueUpdate = false;
        inKeyUpdate = false;

        return values;
    }

    public static class Entry<T, E> implements Map.Entry<T, E> {
        final private OrderedMultiMap<T, E> orderedMap;
        final private int index;
        final private T key;
        private E value;

        public Entry(OrderedMultiMap<T, E> orderedMap, int index) {
            assert orderedMap.keySet().isValidIndex(index);

            this.orderedMap = orderedMap;
            this.index = index;
            this.key = orderedMap.getKey(index);
            this.value = orderedMap.getValue(index);
        }

        @Override
        public T getKey() {
            return key;
        }

        @Override
        public E getValue() {
            return value;
        }

        @Override
        public E setValue(E value) {
            throw new UnsupportedOperationException("Cannot set value on a two way map entry");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?, ?> entry = (Entry<?, ?>) o;

            if (!key.equals(entry.key)) return false;
            if (!value.equals(entry.value)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    public static class ValueKeyEntry<T, E> implements Map.Entry<E, T> {
        final private OrderedMultiMap<T, E> orderedMap;
        final private int index;
        final private E key;
        private T value;

        public ValueKeyEntry(OrderedMultiMap<T, E> orderedMap, int index) {
            assert orderedMap.keySet().isValidIndex(index);

            this.orderedMap = orderedMap;
            this.index = index;
            this.key = orderedMap.getValue(index);
            this.value = orderedMap.getKey(index);
        }

        @Override
        public E getKey() {
            return key;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public T setValue(T value) {
            throw new UnsupportedOperationException("Cannot set value on a two way map entry");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?, ?> entry = (Entry<?, ?>) o;

            if (!key.equals(entry.key)) return false;
            if (!value.equals(entry.value)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            if (key == null) {
                int tmp = 0;
            }
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    public static abstract class AbstractIterator<T, E, X> extends OrderedSet.AbstractIndexedIterator<T, X> {
        protected final OrderedMultiMap<T, E> orderedMap;

        public AbstractIterator(OrderedMultiMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public AbstractIterator(OrderedMultiMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap.keySet, reversed);
            this.orderedMap = orderedMap;
        }
    }

    public static class ValueIterator<T, E> extends AbstractIterator<T, E, E> {
        public ValueIterator(OrderedMultiMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public ValueIterator(OrderedMultiMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<E> reversed() {
            return new ValueIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected E getValueAt(int index) {
            return orderedMap.getValue(index);
        }
    }

    public static class KeyIterator<T, E> extends AbstractIterator<T, E, T> {
        public KeyIterator(OrderedMultiMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public KeyIterator(OrderedMultiMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<T> reversed() {
            return new KeyIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected T getValueAt(int index) {
            return orderedMap.getKey(index);
        }
    }

    public static class KeyValueEntryIterator<T, E> extends AbstractIterator<T, E, Map.Entry<T, E>> {
        public KeyValueEntryIterator(OrderedMultiMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public KeyValueEntryIterator(OrderedMultiMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<Map.Entry<T, E>> reversed() {
            return new KeyValueEntryIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected Entry<T, E> getValueAt(int index) {
            return new Entry<T, E>(orderedMap, index);
        }
    }

    public static class ValueKeyEntryIterator<T, E> extends AbstractIterator<T, E, ValueKeyEntry<T, E>> {
        public ValueKeyEntryIterator(OrderedMultiMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public ValueKeyEntryIterator(OrderedMultiMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<ValueKeyEntry<T, E>> reversed() {
            return new ValueKeyEntryIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected ValueKeyEntry<T, E> getValueAt(int index) {
            return new ValueKeyEntry<T, E>(orderedMap, index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedMultiMap<?, ?> set = (OrderedMultiMap<?, ?>) o;

        if (size() != set.size()) return false;
        if (!entrySet().equals(set.entrySet())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = keySet.hashCode();
        result = 31 * result + valueSet.hashCode();
        return result;
    }
}
