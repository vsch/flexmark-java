package com.vladsch.flexmark.util;

public interface ComputableFactory<P, T> {
    T create(P param);
}
