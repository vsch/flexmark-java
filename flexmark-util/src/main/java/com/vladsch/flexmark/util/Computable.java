package com.vladsch.flexmark.util;

public interface Computable<R, V> {
    R compute(V value);
}
