package com.vladsch.flexmark.internal.util;

public interface ComputableFactory<T, P> {
    T create(P param);
}
