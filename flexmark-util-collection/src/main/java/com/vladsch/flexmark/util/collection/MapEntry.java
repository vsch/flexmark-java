package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

final public class MapEntry<K, V> implements Map.Entry<K, V> {
    final private @NotNull K key;
    final private @Nullable V value;

    public MapEntry(@NotNull K key, @Nullable V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public @NotNull K getKey() {
        return key;
    }

    @Override
    public @Nullable V getValue() {
        return value;
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

        if (!Objects.equals(key, entry.key)) return false;
        return Objects.equals(value, entry.value);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
