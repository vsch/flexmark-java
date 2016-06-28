package com.vladsch.flexmark.internal.util.collection;

public interface Computable<R, V> {
    R compute(V value);
}
