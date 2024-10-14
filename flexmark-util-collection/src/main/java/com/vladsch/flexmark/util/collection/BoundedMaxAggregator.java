package com.vladsch.flexmark.util.collection;

import java.util.function.BinaryOperator;
import org.jetbrains.annotations.Nullable;

public class BoundedMaxAggregator implements BinaryOperator<Integer> {
  private final int maxBound;

  public BoundedMaxAggregator(int maxBound) {
    this.maxBound = maxBound;
  }

  @Override
  public Integer apply(@Nullable Integer aggregate, @Nullable Integer next) {
    if (next != null && next < maxBound) {
      return MaxAggregator.INSTANCE.apply(aggregate, next);
    }

    return aggregate;
  }
}
