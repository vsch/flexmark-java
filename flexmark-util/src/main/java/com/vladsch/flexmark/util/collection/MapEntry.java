package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public final class MapEntry<K, V> implements Map.Entry<K, V> {
    private final @NotNull K myKey;
    private final @Nullable V myValue;

    public MapEntry(@NotNull K key, @Nullable V value) {
        myKey = key;
        myValue = value;
    }

    @Override
    public @NotNull K getKey() {
        return myKey;
    }

    @Override
    public @Nullable V getValue() {
        return myValue;
    }

    @Override
    public @Nullable V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntry<?, ?> entry = (MapEntry<?, ?>) o;

        if (!Objects.equals(myKey, entry.myKey)) return false;
        return Objects.equals(myValue, entry.myValue);
    }

    @Override
    public int hashCode() {
        int result = myKey.hashCode();
        result = 31 * result + (myValue != null ? myValue.hashCode() : 0);
        return result;
    }
}
