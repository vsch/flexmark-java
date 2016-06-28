package com.vladsch.flexmark.internal.util.collection;

public interface Accumulator<T> {
    boolean add(T item);
}
