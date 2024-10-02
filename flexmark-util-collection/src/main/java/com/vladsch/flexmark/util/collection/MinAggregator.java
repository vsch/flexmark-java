package com.vladsch.flexmark.util.collection;

import java.util.function.BinaryOperator;
import org.jetbrains.annotations.Nullable;

public class MinAggregator implements BinaryOperator<Integer> {
  public static final MinAggregator INSTANCE = new MinAggregator();

  private MinAggregator() {}

  @Override
  public @Nullable Integer apply(@Nullable Integer aggregate, @Nullable Integer next) {
    return next == null || aggregate != null && aggregate <= next ? aggregate : next;
  }
}
