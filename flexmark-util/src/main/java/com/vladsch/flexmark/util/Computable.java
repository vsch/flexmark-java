package com.vladsch.flexmark.util;

public interface Computable<V, R> {
    R compute(V value);
}
