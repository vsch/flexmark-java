package com.vladsch.flexmark.internal.util;

public interface ComputeFactory<T, R> {
    T create(R value);
}
