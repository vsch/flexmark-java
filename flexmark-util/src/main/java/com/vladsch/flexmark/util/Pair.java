package com.vladsch.flexmark.util;

import java.util.Objects;

public class Pair<K, V> implements Paired<K, V> {
    public static <K1, V1> Pair<K1, V1> of(K1 first, V1 second) {
        return new Pair<>(first, second);
    }

    private final K first;
    private final V second;

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
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append('(');

        if (first == null) out.append("null");
        else
            out.append(first.getClass().getName().substring(first.getClass().getPackage().getName().length() + 1)).append(' ').append(first);

        out.append(", ");

        if (second == null) out.append("null");
        else
            out.append(second.getClass().getName().substring(second.getClass().getPackage().getName().length() + 1)).append(' ').append(second);

        out.append(')');
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!Objects.equals(first, pair.first)) return false;
        return Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
