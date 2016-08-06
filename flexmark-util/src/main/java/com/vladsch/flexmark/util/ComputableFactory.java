package com.vladsch.flexmark.util;

public interface ComputableFactory<T, P> {
    T create(P param);
}
