package com.vladsch.flexmark.util.misc;

import java.util.Map;
import java.util.Objects;

final public class Pair<K, V> implements Paired<K, V> {
    public static <K1, V1> Pair<K1, V1> of(K1 first, V1 second) {
        return new Pair<>(first, second);
    }

    final private K first;
    final private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public K getFirst() {
        return first;
    }

    @Override
    public V getSecond() {
        return second;
    }

    @Override
    public K getKey() {
        return first;
    }

    @Override
    public V getValue() {
        return second;
    }

    @Override
    public V setValue(V value) {
        throw new IllegalStateException("setValue not supported");
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append('(');

        if (first == null) out.append("null");
        else out.append(first);

        out.append(", ");

        if (second == null) out.append("null");
        else out.append(second);

        out.append(')');
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Map.Entry<?, ?>)) return false;

        Map.Entry<?, ?> pair = (Map.Entry<?, ?>) o;

        if (!Objects.equals(first, pair.getKey())) return false;
        return Objects.equals(second, pair.getValue());
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
