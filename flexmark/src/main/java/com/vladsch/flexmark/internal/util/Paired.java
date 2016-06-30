package com.vladsch.flexmark.internal.util;

public interface Paired<K, V> {
    K getFirst();
    V getSecond();
}
