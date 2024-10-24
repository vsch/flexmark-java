package com.vladsch.flexmark.util.collection;

import java.util.function.BinaryOperator;

public class MinAggregator implements BinaryOperator<Integer> {
  public static final MinAggregator INSTANCE = new MinAggregator();

  private MinAggregator() {}

  @Override
  public Integer apply(Integer aggregate, Integer next) {
    return next == null || aggregate != null && aggregate <= next ? aggregate : next;
  }
}
