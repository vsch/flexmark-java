package com.vladsch.flexmark.util.misc;

public interface Mutable<M extends Mutable<M, I>, I extends Immutable<I, M>> {
  I toImmutable();
}
