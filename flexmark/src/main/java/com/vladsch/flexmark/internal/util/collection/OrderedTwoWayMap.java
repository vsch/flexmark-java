package com.vladsch.flexmark.internal.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OrderedTwoWayMap<K, V> implements Map<K, V> {
    final private OrderedSet<K> keySet;
    final private OrderedSet<V> valueSet;

    public OrderedTwoWayMap() {
        this(0);
    }

    public OrderedTwoWayMap(int capacity) {
        this.valueSet = new OrderedSet<V>(capacity, new OrderedSetHost<V>() {
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
                clearingValues();
            }

            @Override
            public void addingNull(int index) {
                addingNullValue(index);
            }

            @Override
            public boolean hostInUpdate() {
                return keySet.inHostUpdate();
            }
        });

        this.keySet = new OrderedSet<K>(capacity, new OrderedSetHost<K>() {
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
                clearingKeys();
            }

            @Override
            public void addingNull(int index) {
                addingNullKey(index);
            }

            @Override
            public boolean hostInUpdate() {
                return valueSet.inHostUpdate();
            }
        });
    }

    private void addingKey(int index, K k, Object v) {
        if (!valueSet.inHostUpdate()) {
            if (v == null) valueSet.addNull(index);
            valueSet.add((V) v);
        }
    }

    private void addingNullKey(int index) {
        if (!valueSet.inHostUpdate()) {
            while (valueSet.size() <= index) valueSet.add(null);
        }
    }

    private Object removingKey(int index, K k) {
        if (!valueSet.inHostUpdate()) {
            return valueSet.removeHosted(index);
        }
        return null;
    }

    private void clearingKeys() {
        if (!valueSet.inHostUpdate()) {
            valueSet.clear();
        }
    }

    private void addingValue(int index, V V, Object k) {
        if (!keySet.inHostUpdate()) {
            if (k == null) keySet.addNull(index);
            keySet.add((K) k);
        }
    }

    private void addingNullValue(int index) {
        if (!keySet.inHostUpdate()) {
            while (keySet.size() <= index) keySet.add(null);
        }
    }

    private Object removingValue(int index, V v) {
        if (!keySet.inHostUpdate()) {
            return keySet.removeHosted(index);
        }
        return null;
    }

    private void clearingValues() {
        if (!keySet.inHostUpdate()) {
            keySet.clear();
        }
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

    public V putKeyValue(K k, V v) {
        int index = keySet.indexOf(k);
        if (index == -1) {
            keySet.add(k, v);
            return null;
        }

        int i = valueSet.indexOf(v);
        assert i == index : "keySet and valueSet are out of sync";
        return v;
    }

    public K putValueKey(V v, K k) {
        int index = valueSet.indexOf(v);
        if (index == -1) {
            valueSet.add(v, k);
            return null;
        }

        int i = keySet.indexOf(k);
        assert i == index : "keySet and valueSet are out of sync";
        return k;
    }

    @Override
    public V remove(Object o) {
        return removeKey(o);
    }

    public V removeKey(Object o) {
        return (V) keySet.removeHosted(o);
    }

    public K removeValue(Object o) {
        return (K) valueSet.removeHosted(o);
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
        keySet.clear();
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

    public static class Entry<T, E> implements Map.Entry<T, E> {
        final private OrderedTwoWayMap<T, E> orderedMap;
        final private int index;
        final private T key;
        private E value;

        public Entry(OrderedTwoWayMap<T, E> orderedMap, int index) {
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
        final private OrderedTwoWayMap<T, E> orderedMap;
        final private int index;
        final private E key;
        private T value;

        public ValueKeyEntry(OrderedTwoWayMap<T, E> orderedMap, int index) {
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

    @Override
    public OrderedSet<Map.Entry<K, V>> entrySet() {
        return keyValueEntrySet();
    }

    public OrderedSet<Map.Entry<K, V>> keyValueEntrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        OrderedSet<Map.Entry<K, V>> values = new OrderedSet<>(keySet.size(), true, new OrderedSetHost<Map.Entry<K, V>>() {
            @Override
            public void adding(int index, Map.Entry<K, V> entry, Object v) {
                assert v == null;
                OrderedTwoWayMap.this.keySet.add(entry.getKey(), entry.getValue());
            }

            @Override
            public Object removing(int index, Map.Entry<K, V> entry) {
                OrderedTwoWayMap.this.keySet.remove(index);
                return entry;
            }

            @Override
            public void clearing() {
                OrderedTwoWayMap.this.keySet.clear();
            }

            @Override
            public void addingNull(int index) {
                OrderedTwoWayMap.this.keySet.addNull(index);
            }

            @Override
            public boolean hostInUpdate() {
                return OrderedTwoWayMap.this.keySet.inHostUpdate();
            }
        });

        SparseIterator<Integer> iterator = keySet.indexIterator();
        while (iterator.hasNext()) {
            iterator.next();
            values.add(new Entry<K, V>(this, iterator.getIndex()));
        }
        // release it for host update
        values.leaveHostUpdate();

        return values;
    }

    public OrderedSet<Map.Entry<V, K>> valueKeyEntrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        OrderedSet<Map.Entry<V, K>> values = new OrderedSet<>(valueSet.size(), true, new OrderedSetHost<Map.Entry<V, K>>() {
            @Override
            public void adding(int index, Map.Entry<V, K> entry, Object k) {
                assert k == null;
                OrderedTwoWayMap.this.valueSet.add(entry.getKey(), entry.getValue());
            }

            @Override
            public Object removing(int index, Map.Entry<V, K> entry) {
                OrderedTwoWayMap.this.valueSet.remove(index);
                return entry;
            }

            @Override
            public void clearing() {
                OrderedTwoWayMap.this.valueSet.clear();
            }

            @Override
            public void addingNull(int index) {
                OrderedTwoWayMap.this.valueSet().addNull(index);
            }
            
            @Override
            public boolean hostInUpdate() {
                return OrderedTwoWayMap.this.valueSet.inHostUpdate();
            }
        });

        SparseIterator<Integer> iterator = keySet.indexIterator();
        while (iterator.hasNext()) {
            iterator.next();
            values.add(new ValueKeyEntry<K, V>(this, iterator.getIndex()));
        }
        // release it for host update
        values.leaveHostUpdate();

        return values;
    }

    public static abstract class AbstractIterator<T, E, X> extends OrderedSet.AbstractIndexedIterator<T, X> {
        protected final OrderedTwoWayMap<T, E> orderedMap;

        public AbstractIterator(OrderedTwoWayMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public AbstractIterator(OrderedTwoWayMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap.keySet, reversed);
            this.orderedMap = orderedMap;
        }
    }

    public static class ValueIterator<T, E> extends AbstractIterator<T, E, E> {
        public ValueIterator(OrderedTwoWayMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public ValueIterator(OrderedTwoWayMap<T, E> orderedMap, boolean reversed) {
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
        public KeyIterator(OrderedTwoWayMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public KeyIterator(OrderedTwoWayMap<T, E> orderedMap, boolean reversed) {
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

    public static class KeyValueEntryIterator<T, E> extends AbstractIterator<T, E, Entry<T, E>> {
        public KeyValueEntryIterator(OrderedTwoWayMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public KeyValueEntryIterator(OrderedTwoWayMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<Entry<T, E>> reversed() {
            return new KeyValueEntryIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected Entry<T, E> getValueAt(int index) {
            return new Entry<T, E>(orderedMap, index);
        }
    }

    public static class ValueKeyEntryIterator<T, E> extends AbstractIterator<T, E, ValueKeyEntry<T, E>> {
        public ValueKeyEntryIterator(OrderedTwoWayMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public ValueKeyEntryIterator(OrderedTwoWayMap<T, E> orderedMap, boolean reversed) {
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

    public ReversibleIterator<Entry<K, V>> entryIterator() {
        return keyValueEntryIterator();
    }

    public ReversibleIterator<Entry<K, V>> reversedEntryIterator() {
        return reversedKeyValueEntryIterator();
    }

    public ReversibleIterator<Entry<K, V>> keyValueEntryIterator() {
        return new KeyValueEntryIterator<K, V>(this);
    }

    public ReversibleIterator<Entry<K, V>> reversedKeyValueEntryIterator() {
        return new KeyValueEntryIterator<K, V>(this, true);
    }

    public ReversibleIterator<ValueKeyEntry<K, V>> valueKeyEntryIterator() {
        return new ValueKeyEntryIterator<K, V>(this);
    }

    public ReversibleIterator<ValueKeyEntry<K, V>> reversedValueKeyEntryIterator() {
        return new ValueKeyEntryIterator<K, V>(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedTwoWayMap<?, ?> set = (OrderedTwoWayMap<?, ?>) o;

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
