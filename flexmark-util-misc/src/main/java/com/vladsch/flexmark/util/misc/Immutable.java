package com.vladsch.flexmark.util.misc;

public interface Immutable<I extends Immutable<I, M>, M extends Mutable<M, I>> {
  M toMutable();
}
