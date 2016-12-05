package com.vladsch.flexmark.util;

public class Pair<K, V> implements Paired<K, V> {
    public static <K1, V1> Pair<K1, V1> of(K1 first, V1 second) {
        return new Pair<K1, V1>(first, second);
    }

    private final K myFirst;
    private final V mySecond;

    public Pair(K first, V second) {
        this.myFirst = first;
        this.mySecond = second;
    }

    @Override
    public K getFirst() {
        return myFirst;
    }

    @Override
    public V getSecond() {
        return mySecond;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append('(');

        if (myFirst == null) out.append("null");
        else
            out.append(myFirst.getClass().getName().substring(myFirst.getClass().getPackage().getName().length() + 1)).append(' ').append(myFirst);

        out.append(", ");

        if (mySecond == null) out.append("null");
        else
            out.append(mySecond.getClass().getName().substring(mySecond.getClass().getPackage().getName().length() + 1)).append(' ').append(mySecond);

        out.append(')');
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (myFirst != null ? !myFirst.equals(pair.myFirst) : pair.myFirst != null) return false;
        return mySecond != null ? mySecond.equals(pair.mySecond) : pair.mySecond == null;
    }

    @Override
    public int hashCode() {
        int result = myFirst != null ? myFirst.hashCode() : 0;
        result = 31 * result + (mySecond != null ? mySecond.hashCode() : 0);
        return result;
    }
}
