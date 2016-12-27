package com.vladsch.flexmark.util;

public interface Immutable<I extends Immutable<I, M>, M extends Mutable<M, I>> {
    M toMutable();
}
