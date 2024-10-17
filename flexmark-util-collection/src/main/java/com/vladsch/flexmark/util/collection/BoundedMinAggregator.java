package com.vladsch.flexmark.util.collection;

import java.util.function.BinaryOperator;
import org.jetbrains.annotations.Nullable;

public class BoundedMinAggregator implements BinaryOperator<Integer> {
  private final int minBound;

  public BoundedMinAggregator(int minBound) {
    this.minBound = minBound;
  }

  @Override
  public Integer apply(@Nullable Integer aggregate, @Nullable Integer next) {
    if (next != null && next > minBound) {
      return MinAggregator.INSTANCE.apply(aggregate, next);
    }
    return aggregate;
  }
}
