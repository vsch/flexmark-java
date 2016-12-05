package com.vladsch.flexmark.util.collection;

import java.util.Map;

public final class MapEntry<K, V> implements Map.Entry<K, V> {
    private final K myKey;
    private final V myValue;

    public MapEntry(K key, V value) {
        myKey = key;
        myValue = value;
    }

    @Override
    public K getKey() {
        return myKey;
    }

    @Override
    public V getValue() {
        return myValue;
    }

    @Override
    public V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntry<?, ?> entry = (MapEntry<?, ?>) o;

        if (myKey != null ? !myKey.equals(entry.myKey) : entry.myKey != null) return false;
        return myValue != null ? myValue.equals(entry.myValue) : entry.myValue == null;
    }

    @Override
    public int hashCode() {
        int result = myKey != null ? myKey.hashCode() : 0;
        result = 31 * result + (myValue != null ? myValue.hashCode() : 0);
        return result;
    }
}
