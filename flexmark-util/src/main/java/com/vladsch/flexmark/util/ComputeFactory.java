package com.vladsch.flexmark.util;

public interface ComputeFactory<T, R> {
    T create(R value);
}
