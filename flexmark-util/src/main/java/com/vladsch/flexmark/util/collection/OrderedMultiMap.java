package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Paired;
import com.vladsch.flexmark.util.collection.iteration.*;

import java.util.*;

public class OrderedMultiMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
    private final OrderedSet<K> myKeySet;
    private final OrderedSet<V> myValueSet;
    private final CollectionHost<Paired<K, V>> myHost;
    private boolean myInKeyUpdate;
    private boolean myInValueUpdate;
    private Indexed<Entry<K, V>> myIndexedProxy;

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
        this.myHost = host;
        this.myIndexedProxy = null;
        this.myValueSet = new OrderedSet<V>(capacity, new CollectionHost<V>() {
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
                return myInKeyUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        });

        this.myKeySet = new OrderedSet<K>(capacity, new CollectionHost<K>() {
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
                return myInValueUpdate;
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
        return new MapEntry<K, V>(myKeySet.getValueOrNull(index), myValueSet.getValueOrNull(index));
    }

    public int getModificationCount() {
        return (int) ((long) myKeySet.getModificationCount() + (long) myValueSet.getModificationCount());
    }

    @SuppressWarnings("unchecked")
    private void addingKey(int index, K k, Object v) {
        assert !myInValueUpdate;

        myInValueUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.adding(index, new Pair<K, V>(k, (V) v), null);
        }
        if (v == null) myValueSet.addNulls(index);
        else myValueSet.add((V) v);
        myInValueUpdate = false;
    }

    private void addingNullKey(int index) {
        assert !myInValueUpdate;

        myInValueUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.addingNulls(index);
        }
        while (valueSet().size() <= index) myValueSet.add(null);
        myInValueUpdate = false;
    }

    private Object removingKey(int index, K k) {
        assert !myInValueUpdate;

        myInValueUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.removing(index, new Pair<K, V>(k, null));
        }
        Object r = myValueSet.removeIndexHosted(index);
        myInValueUpdate = false;
        return r;
    }

    @SuppressWarnings("unchecked")
    private void addingValue(int index, V v, Object k) {
        assert !myInKeyUpdate;

        myInKeyUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.adding(index, new Pair<K, V>((K) k, v), null);
        }
        if (k == null) myKeySet.addNulls(index);
        else myKeySet.add((K) k);
        myInKeyUpdate = false;
    }

    private void addingNullValue(int index) {
        assert !myInKeyUpdate;

        myInKeyUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.addingNulls(index);
        }
        while (myKeySet.size() <= index) myKeySet.add(null);
        myInKeyUpdate = false;
    }

    private Object removingValue(int index, V v) {
        assert !myInKeyUpdate;

        myInKeyUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.removing(index, new Pair<K, V>((K)null, v));
        }
        Object r = myKeySet.removeIndexHosted(index);
        myInKeyUpdate = false;
        return r;
    }

    @Override
    public int size() {
        return myKeySet.size();
    }

    @Override
    public boolean isEmpty() {
        return myKeySet.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return myKeySet.contains(o);
    }

    @Override
    public boolean containsValue(Object o) {
        int index = myValueSet.indexOf(o);
        return myKeySet.isValidIndex(index);
    }

    @Override
    public V get(Object o) {
        return getKeyValue(o);
    }

    public V getKeyValue(Object o) {
        int index = myKeySet.indexOf(o);
        return index == -1 ? null : myValueSet.getValue(index);
    }

    public K getValueKey(Object o) {
        int index = myValueSet.indexOf(o);
        return index == -1 ? null : myKeySet.getValue(index);
    }

    @Override
    public V put(K k, V v) {
        return putKeyValue(k, v);
    }

    public void addNullEntry(int index) {
        myInKeyUpdate = true;
        myInValueUpdate = true;

        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.addingNulls(index);
        }
        myKeySet.addNulls(index);
        myValueSet.addNulls(index);

        myInValueUpdate = false;
        myInKeyUpdate = false;
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
        int keyIndex = myKeySet.indexOf(k);
        int valueIndex = myValueSet.indexOf(v);

        if (keyIndex == -1 && valueIndex == -1) {
            // neither one exists/ we add both
            myInKeyUpdate = true;
            myInValueUpdate = true;
            if (myHost != null && !myHost.skipHostUpdate()) {
                myHost.adding(myKeySet.getValueList().size(), new Pair<K, V>(k, v), null);
            }

            if (k == null) myKeySet.addNull();
            else myKeySet.add(k, v);

            if (k == null) myValueSet.addNull();
            else myValueSet.add(v, k);

            myInValueUpdate = false;
            myInKeyUpdate = false;

            return true;
        }

        if (keyIndex == -1) {
            myInKeyUpdate = true;
            myInValueUpdate = true;
            if (myHost != null && !myHost.skipHostUpdate()) {
                myHost.adding(valueIndex, new Pair<K, V>(k, v), null);
            }

            if (k == null) myKeySet.removeIndex(valueIndex);
            else myKeySet.setValueAt(valueIndex, k, v);

            myInValueUpdate = false;
            myInKeyUpdate = false;
            return true;
        }

        if (valueIndex == -1) {
            myInKeyUpdate = true;
            myInValueUpdate = true;
            if (myHost != null && !myHost.skipHostUpdate()) {
                myHost.adding(keyIndex, new Pair<K, V>(k, v), null);
            }

            if (k == null) myValueSet.removeIndex(valueIndex);
            else myValueSet.setValueAt(keyIndex, v, k);

            myInValueUpdate = false;
            myInValueUpdate = false;
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
        return removeEntryIndex(index, myKeySet.getValueOrNull(index), myValueSet.getValueOrNull(index));
    }

    private boolean removeEntryIndex(int index, K k, V v) {
        int keyIndex = myKeySet.indexOf(k);
        int valueIndex = myValueSet.indexOf(v);

        if (keyIndex != valueIndex) {
            throw new IllegalStateException("keySet[" + keyIndex + "]=" + k + " and valueSet[" + valueIndex + "]=" + v + " are out of sync");
        }

        if (index != -1 && keyIndex != index) {
            throw new IllegalStateException("removeEntryIndex " + index + " does not match keySet[" + keyIndex + "]=" + k + " and valueSet[" + valueIndex + "]=" + v + " are out of sync");
        }

        if (keyIndex != -1) {
            myInKeyUpdate = true;
            myInValueUpdate = true;
            if (myHost != null && !myHost.skipHostUpdate()) {
                myHost.removing(keyIndex, new Pair<K, V>(k, v));
            }
            myKeySet.removeHosted(k);
            myValueSet.removeHosted(v);
            myInValueUpdate = false;
            myInKeyUpdate = false;
            return true;
        }
        return false;
    }

    public V removeKey(Object o) {
        myInKeyUpdate = true;
        if (myHost != null && !myHost.skipHostUpdate()) {
            int index = myKeySet.indexOf(o);
            if (index != -1) {
                myHost.removing(index, new Pair<K, V>((K) o, myValueSet.isValidIndex(index) ? myValueSet.getValue(index) : null));
            }
        }
        V r = (V) myKeySet.removeHosted(o);
        myInKeyUpdate = false;
        return r;
    }

    public K removeValue(Object o) {
        myInValueUpdate = true;
        int index = myValueSet.indexOf(o);
        if (myHost != null && !myHost.skipHostUpdate()) {
            if (index != -1) {
                myHost.removing(index, new Pair<K, V>(myKeySet.isValidIndex(index) ? myKeySet.getValue(index) : null, (V) o));
            }
        }
        K r = (K) myValueSet.removeHosted(o);
        myInValueUpdate = false;
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
        myInValueUpdate = true;
        myInKeyUpdate = true;

        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.clearing();
        }
        myKeySet.clear();
        myValueSet.clear();

        myInKeyUpdate = false;
        myInValueUpdate = false;
    }

    @Override
    public OrderedSet<K> keySet() {
        return myKeySet;
    }

    @Override
    public Collection<V> values() {
        if (!myKeySet.isSparse()) {
            return myValueSet;
        }

        ArrayList<V> values = new ArrayList<V>(myKeySet.size());
        ReversibleIndexedIterator<V> iterator = myValueSet.iterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    public OrderedSet<V> valueSet() {
        return myValueSet;
    }

    public Collection<K> keys() {
        if (!myKeySet.isSparse()) {
            return myKeySet;
        }

        ArrayList<K> values = new ArrayList<K>(myValueSet.size());
        ReversibleIndexedIterator<K> iterator = myKeySet.iterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    public K getKey(int index) {
        if (!myKeySet.isValidIndex(index)) return null;
        return myKeySet.getValueList().get(index);
    }

    public V getValue(int index) {
        if (!myValueSet.isValidIndex(index)) return null;
        return myValueSet.getValue(index);
    }

    @Override
    public OrderedSet<Map.Entry<K, V>> entrySet() {
        return keyValueEntrySet();
    }

    public ReversibleIndexedIterator<V> valueIterator() {
        return myValueSet.iterator();
    }

    public ReversibleIndexedIterator<V> reversedValueIterator() {
        return myValueSet.reversedIterator();
    }

    public ReversibleIterable<V> valueIterable() {
        return new IndexedIterable<V, V, ReversibleIterable<Integer>>(myValueSet.getIndexedProxy(), myValueSet.indexIterable());
    }

    public ReversibleIterable<V> reversedValueIterable() {
        return new IndexedIterable<V, V, ReversibleIterable<Integer>>(myValueSet.getIndexedProxy(), myValueSet.reversedIndexIterable());
    }

    public ReversibleIndexedIterator<K> keyIterator() {
        return keySet().iterator();
    }

    public ReversibleIndexedIterator<K> reversedKeyIterator() {
        return keySet().reversedIterator();
    }

    public ReversibleIterable<K> keyIterable() {
        return new IndexedIterable<K, K, ReversibleIterable<Integer>>(myKeySet.getIndexedProxy(), myKeySet.indexIterable());
    }

    public ReversibleIterable<K> reversedKeyIterable() {
        return new IndexedIterable<K, K, ReversibleIterable<Integer>>(myKeySet.getIndexedProxy(), myKeySet.reversedIndexIterable());
    }

    public ReversibleIndexedIterator<Entry<K, V>> entrySetIterator() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterator<Entry<K, V>, Entry<K, V>, BitSetIterator>(getIndexedProxy(), new BitSetIterator(bitSet));
    }

    public ReversibleIndexedIterator<Entry<K, V>> reversedEntrySetIterator() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterator<Entry<K, V>, Entry<K, V>, BitSetIterator>(getIndexedProxy(), new BitSetIterator(bitSet, true));
    }

    public ReversibleIterable<Entry<K, V>> entrySetIterable() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterable<Entry<K, V>, Entry<K, V>, BitSetIterable>(getIndexedProxy(), new BitSetIterable(bitSet));
    }

    public ReversibleIterable<Entry<K, V>> reversedEntrySetIterable() {
        BitSet bitSet = getKeyValueUnionSet();
        return new IndexedIterable<Entry<K, V>, Entry<K, V>, BitSetIterable>(getIndexedProxy(), new BitSetIterable(bitSet));
    }

    private BitSet getKeyValueUnionSet() {
        BitSet bitSet = new BitSet(myKeySet.size());
        bitSet.or(myKeySet.getValidIndices());
        bitSet.or(myValueSet.getValidIndices());
        return bitSet;
    }

    private BitSet getKeyValueIntersectionSet() {
        BitSet bitSet = new BitSet(myKeySet.size());
        bitSet.or(myKeySet.getValidIndices());
        bitSet.and(myValueSet.getValidIndices());
        return bitSet;
    }

    /*
     * Iterable
     */

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return entrySetIterator();
    }

    public void forEach(Consumer<? super Entry<K, V>> consumer) {
        Iterator<Map.Entry<K, V>> iterator = entrySetIterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

    public OrderedSet<Map.Entry<K, V>> keyValueEntrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        myInValueUpdate = true;
        myInKeyUpdate = true;

        OrderedSet<Map.Entry<K, V>> values = new OrderedSet<Entry<K, V>>(myKeySet.size(), new CollectionHost<Map.Entry<K, V>>() {
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
                return myInKeyUpdate || myInValueUpdate;
            }

            @Override
            public int getIteratorModificationCount() {
                return OrderedMultiMap.this.getModificationCount();
            }
        });

        Iterator<Map.Entry<K, V>> iterator = entrySetIterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }

        // release it for host update
        myInValueUpdate = false;
        myInKeyUpdate = false;

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
        int result = myKeySet.hashCode();
        result = 31 * result + myValueSet.hashCode();
        return result;
    }
}
