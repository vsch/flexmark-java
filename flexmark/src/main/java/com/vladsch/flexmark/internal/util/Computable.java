package com.vladsch.flexmark.internal.util;

public interface Computable<R, V> {
    R compute(V value);
}
