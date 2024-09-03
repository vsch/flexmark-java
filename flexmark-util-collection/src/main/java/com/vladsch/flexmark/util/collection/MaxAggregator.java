package com.vladsch.flexmark.util.collection;

import java.util.function.BiFunction;
import org.jetbrains.annotations.Nullable;

public class MaxAggregator implements BiFunction<Integer, Integer, Integer> {
  public static final MaxAggregator INSTANCE = new MaxAggregator();

  private MaxAggregator() {}

  @Override
  public @Nullable Integer apply(@Nullable Integer aggregate, @Nullable Integer next) {
    return next == null || aggregate != null && aggregate >= next ? aggregate : next;
  }
}
