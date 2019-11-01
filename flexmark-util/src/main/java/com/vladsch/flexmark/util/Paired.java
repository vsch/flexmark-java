package com.vladsch.flexmark.util;

public interface Paired<K, V> {
    K getFirst();
    V getSecond();

    // Kotlin destructuring
    default K component1() {
        return getFirst();
    }

    default V component2() {
        return getSecond();
    }
}
