package com.vladsch.flexmark.util;

public interface Paired<K, V> {
    K getFirst();
    V getSecond();
}
