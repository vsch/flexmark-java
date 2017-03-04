package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.Function;
import com.vladsch.flexmark.util.collection.iteration.*;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class OrderedMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {
    private final OrderedSet<K> keySet;
    private final ArrayList<V> valueList;
    private final CollectionHost<K> host;
    private boolean inUpdate;
    private Indexed<Entry<K, V>> myIndexedEntryProxy;
    private Indexed<V> myIndexedValueProxy;

    public OrderedMap() {
        this(0, null);
    }

    public OrderedMap(int capacity) {
        this(capacity, null);
    }

    public OrderedMap(CollectionHost<K> host) {
        this(0, host);
    }

    public OrderedMap(int capacity, CollectionHost<K> host) {
        this.valueList = new ArrayList<V>(capacity);
        this.host = host;
        this.myIndexedEntryProxy = null;
        this.myIndexedValueProxy = null;
        this.keySet = new OrderedSet<K>(capacity, new CollectionHost<K>() {
            @Override
            public void adding(int index, K k, Object v) {
                OrderedMap.this.adding(index, k, v);
            }

            @Override
            public Object removing(int index, K k) {
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

    public Indexed<Map.Entry<K, V>> getIndexedEntryProxy() {
        if (myIndexedEntryProxy != null) return myIndexedEntryProxy;
        myIndexedEntryProxy = new Indexed<Map.Entry<K, V>>() {
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

        return myIndexedEntryProxy;
    }

    public Indexed<V> getIndexedValueProxy() {
        if (myIndexedValueProxy != null) return myIndexedValueProxy;
        myIndexedValueProxy = new Indexed<V>() {
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

        return myIndexedValueProxy;
    }

    private Map.Entry<K, V> getEntry(int index) {
        return new MapEntry<K, V>(keySet.getValue(index), valueList.get(index));
    }

    public int getModificationCount() {
        return keySet.getModificationCount();
    }

    void adding(int index, K k, Object v) {
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

    Object removing(int index, K k) {
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
    public boolean containsKey(Object o) {
        //noinspection SuspiciousMethodCalls
        return keySet.contains(o);
    }

    @Override
    public boolean containsValue(Object o) {
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
    public V get(Object o) {
        int index = keySet.indexOf(o);
        return index == -1 ? null : valueList.get(index);
    }

    @Override
    public V put(K k, V v) {
        int index = keySet.indexOf(k);
        if (index == -1) {
            keySet.add(k, v);
            return null;
        }

        V old = valueList.get(index);
        valueList.set(index, v);
        return old;
    }

    public V computeIfMissing(K k, Function<? super K, ? extends V> runnableValue) {
        int index = keySet.indexOf(k);
        if (index == -1) {
            V v = runnableValue.apply(k);
            keySet.add(k, v);
            return v;
        }

        return valueList.get(index);
    }

    @Override
    public V remove(Object o) {
        //noinspection unchecked
        return (V) keySet.removeHosted(o);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void addAll(Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        for (Map.Entry<? extends K, ? extends V> entry : entries) {
            put(entry.getKey(), entry.getValue());
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
            return valueList;
        }

        ArrayList<V> values = new ArrayList<V>(keySet.size());
        Iterator<Integer> iterator = keySet.indexIterator();
        while (iterator.hasNext()) {
            values.add(valueList.get(iterator.next()));
        }
        return values;
    }

    public K getKey(int index) {
        if (!keySet.isValidIndex(index)) return null;
        return keySet.getValueList().get(index);
    }

    public V getValue(int index) {
        if (!keySet.isValidIndex(index)) return null;
        return valueList.get(index);
    }

    @Override
    public OrderedSet<Map.Entry<K, V>> entrySet() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        inUpdate = true;
        OrderedSet<Map.Entry<K, V>> values = new OrderedSet<Entry<K, V>>(keySet.size(), new EntryCollectionHost<K, V>());
        Iterator<Map.Entry<K, V>> iterator = entryIterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }

        // release it for host update
        inUpdate = false;

        return values;
    }

    public List<Entry<K, V>> entries() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        List<Map.Entry<K, V>> values = new ArrayList<Entry<K, V>>();
        Iterator<Map.Entry<K, V>> iterator = entryIterator();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    public List<K> keys() {
        // create it with inHostUpdate already set so we can populate it without callbacks
        return keySet.values();
    }

    public ReversibleIndexedIterator<V> valueIterator() {
        return new IndexedIterator<V, V, ReversibleIterator<Integer>>(getIndexedValueProxy(), keySet.indexIterator());
    }

    public ReversibleIndexedIterator<V> reversedValueIterator() {
        return new IndexedIterator<V, V, ReversibleIterator<Integer>>(getIndexedValueProxy(), keySet.reversedIndexIterator());
    }

    public ReversibleIndexedIterator<K> keyIterator() {
        return keySet.iterator();
    }

    public ReversibleIndexedIterator<K> reversedKeyIterator() {
        return keySet.reversedIterator();
    }

    public ReversibleIndexedIterator<Map.Entry<K, V>> entryIterator() {
        return new IndexedIterator<Entry<K, V>, Entry<K, V>, ReversibleIterator<Integer>>(getIndexedEntryProxy(), keySet.indexIterator());
    }

    public ReversibleIndexedIterator<Map.Entry<K, V>> reversedEntryIterator() {
        return new IndexedIterator<Entry<K, V>, Entry<K, V>, ReversibleIterator<Integer>>(getIndexedEntryProxy(), keySet.reversedIndexIterator());
    }

    public ReversibleIndexedIterator<Map.Entry<K, V>> reversedIterator() {
        return reversedEntryIterator();
    }

    public ReversibleIterable<V> valueIterable() {
        return new IndexedIterable<V, V, ReversibleIterable<Integer>>(getIndexedValueProxy(), keySet.indexIterable());
    }

    public ReversibleIterable<V> reversedValueIterable() {
        return new IndexedIterable<V, V, ReversibleIterable<Integer>>(getIndexedValueProxy(), keySet.reversedIndexIterable());
    }

    public ReversibleIterable<K> keyIterable() {
        return keySet.iterable();
    }

    public ReversibleIterable<K> reversedKeyIterable() {
        return keySet.reversedIterable();
    }

    public ReversibleIterable<Map.Entry<K, V>> entryIterable() {
        return new IndexedIterable<Entry<K, V>, Entry<K, V>, ReversibleIterable<Integer>>(getIndexedEntryProxy(), keySet.indexIterable());
    }

    public ReversibleIterable<Entry<K, V>> reversedEntryIterable() {
        return new IndexedIterable<Entry<K, V>, Entry<K, V>, ReversibleIterable<Integer>>(getIndexedEntryProxy(), keySet.reversedIndexIterable());
    }

    public ReversibleIterable<Map.Entry<K, V>> reversedIterable() {
        return reversedEntryIterable();
    }

    /*
     * Iterable
     */

    @Override
    public ReversibleIndexedIterator<Map.Entry<K, V>> iterator() {
        return entryIterator();
    }

    public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
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
        if (!entrySet().equals(set.entrySet())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = keySet.hashCode();
        result = 31 * result + valueList.hashCode();
        return result;
    }

    private class EntryCollectionHost<KK extends K, VV extends V> implements CollectionHost<Map.Entry<KK, VV>> {
        @Override
        public void adding(int index, Entry<KK, VV> entry, Object v) {
            assert v == null;
            OrderedMap.this.keySet.add(entry.getKey(), entry.getValue());
        }

        @Override
        public Object removing(int index, Entry<KK, VV> entry) {
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
