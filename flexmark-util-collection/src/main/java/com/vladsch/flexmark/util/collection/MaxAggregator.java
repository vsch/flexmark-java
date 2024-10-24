package com.vladsch.flexmark.util.collection;

import java.util.function.BinaryOperator;

public class MaxAggregator implements BinaryOperator<Integer> {
  public static final MaxAggregator INSTANCE = new MaxAggregator();

  private MaxAggregator() {}

  @Override
  public Integer apply(Integer aggregate, Integer next) {
    return next == null || aggregate != null && aggregate >= next ? aggregate : next;
  }
}
