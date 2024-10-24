package com.vladsch.flexmark.util.collection;

import java.util.function.BinaryOperator;

public class BoundedMaxAggregator implements BinaryOperator<Integer> {
  private final int maxBound;

  public BoundedMaxAggregator(int maxBound) {
    this.maxBound = maxBound;
  }

  @Override
  public Integer apply(Integer aggregate, Integer next) {
    if (next != null && next < maxBound) {
      return MaxAggregator.INSTANCE.apply(aggregate, next);
    }

    return aggregate;
  }
}
