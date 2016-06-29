package com.vladsch.flexmark.internal.util.collection;

import java.util.*;
import java.util.function.Consumer;

public class OrderedMultiMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
    final private OrderedSet<K> keySet;
    final private OrderedSet<V> valueSet;
    final private CollectionHost<Paired<K, V>> host;
    private boolean inKeyUpdate;
    private boolean inValueUpdate;
    private Indexed<Map.Entry<K, V>> myIndexedProxy;

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
        this.myIndexedProxy = null;
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

    public Indexed<Map.Entry<K, V>> getIndexedProxy() {
        if (myIndexedProxy != null) return myIndexedProxy;
        myIndexedProxy = new Indexed<Map.Entry<K, V>>() {
            @Override
            public Map.Entry<K, V> get(int index) {
                return OrderedMultiMap.this.getEntry(index);
            }

            @Override
            public void set(int index, Map.Entry<K, V> item) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void removeAt(int index) {
                OrderedMultiMap.this.removeEntryIndex(index);
            }

            @Override
            public int size() {
                return OrderedMultiMap.this.size();
            }

            @Override
            public int modificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        };

        return myIndexedProxy;
    }

    private Map.Entry<K, V> getEntry(int index) {
        return new MapEntry<K, V>(keySet.getValueOrNull(index), valueSet.getValueOrNull(index));
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

            if (k == null) keySet.addNull();
            else keySet.add(k, v);

            if (k == null) valueSet.addNull();
            else valueSet.add(v, k);

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

            if (k == null) keySet.removeIndex(valueIndex);
            else keySet.setValueAt(valueIndex, k, v);

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

            if (k == null) valueSet.removeIndex(valueIndex);
            else valueSet.setValueAt(keyIndex, v, k);

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

    private boolean removeEntryIndex(int index) {
        return removeEntryIndex(index, keySet.getValueOrNull(index), valueSet.getValueOrNull(index));
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
        ReversibleIndexedIterator<V> iterator = valueSet.iterator();
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
        ReversibleIndexedIterator<K> iterator = keySet.iterator();
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

    public ReversibleIndexedIterator<V> valueIterator() {
        return valueSet.iterator();
    }

    public ReversibleIndexedIterator<V> reversedValueIterator() {
        return valueSet.reversedIterator();
    }

    public ReversibleIterable<V> valueIterable() {
        return new IndexedIterable<>(valueSet.getIndexedProxy(), valueSet.indexIterable());
    }

    public ReversibleIterable<V> reversedValueIterable() {
        return new IndexedIterable<>(valueSet.getIndexedProxy(), valueSet.reversedIndexIterable());
    }

    public ReversibleIndexedIterator<K> keyIterator() {
        return keySet().iterator();
    }

    public ReversibleIndexedIterator<K> reversedKeyIterator() {
        return keySet().reversedIterator();
    }

    public ReversibleIterable<K> keyIterable() {
        return new IndexedIterable<>(keySet.getIndexedProxy(), keySet.indexIterable());
    }

    public ReversibleIterable<K> reversedKeyIterable() {
        return new IndexedIterable<>(keySet.getIndexedProxy(), keySet.reversedIndexIterable());
    }

    public ReversibleIndexedIterator<Entry<K, V>> entrySetIterator() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterator<>(getIndexedProxy(), new BitSetIterator(bitSet));
    }

    public ReversibleIndexedIterator<Entry<K, V>> reversedEntrySetIterator() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterator<>(getIndexedProxy(), new BitSetIterator(bitSet, true));
    }

    public ReversibleIterable<Entry<K, V>> entrySetIterable() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterable<>(getIndexedProxy(), new BitSetIterable(bitSet));
    }

    public ReversibleIterable<Entry<K, V>> reversedEntrySetIterable() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterable<>(getIndexedProxy(), new BitSetIterable(bitSet));
    }

    private BitSet getKeyValueUnionSet() {
        BitSet bitSet = new BitSet(keySet.size());
        bitSet.or(keySet.getValidIndices());
        bitSet.or(valueSet.getValidIndices());
        return bitSet;
    }

    private BitSet getKeyValueIntersectionSet() {
        BitSet bitSet = new BitSet(keySet.size());
        bitSet.or(keySet.getValidIndices());
        bitSet.and(valueSet.getValidIndices());
        return bitSet;
    }

    /*
     * Iterable
     */

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return entrySetIterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
        Iterator<Map.Entry<K, V>> iterator = entrySetIterator();
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

        BitSet bitSet = getKeyValueUnionSet();
        Iterator<Map.Entry<K, V>> iterator = entrySetIterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }

        // release it for host update
        inValueUpdate = false;
        inKeyUpdate = false;

        return values;
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
