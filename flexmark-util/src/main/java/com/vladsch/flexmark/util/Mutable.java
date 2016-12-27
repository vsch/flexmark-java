package com.vladsch.flexmark.util;

public interface Mutable<M extends Mutable<M, I>, I extends Immutable<I, M>> {
    I toImmutable();
}
