package com.vladsch.flexmark.util.collection;

import java.util.function.UnaryOperator;

public class CopyOnWriteRef<T> {
  private T value;
  private int referenceCount;
  private final UnaryOperator<T> copyFunction;

  public CopyOnWriteRef(T value, UnaryOperator<T> copyFunction) {
    this.value = value;
    referenceCount = 0;
    this.copyFunction = copyFunction;
  }

  public T getPeek() {
    return value;
  }

  public T getImmutable() {
    if (value != null) referenceCount++;
    return value;
  }

  public T getMutable() {
    if (referenceCount > 0) {
      value = copyFunction.apply(value);
      referenceCount = 0;
    }
    return value;
  }

  public void setValue(T value) {
    referenceCount = 0;
    this.value = copyFunction.apply(value);
  }
}
