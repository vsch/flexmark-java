package com.vladsch.flexmark.util;

public interface ComputeFactory<R, T> {
    T create(R value);
}
