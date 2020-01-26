package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class MaxAggregator implements BiFunction<Integer, Integer, Integer> {
    final public static MaxAggregator INSTANCE = new MaxAggregator();

    private MaxAggregator() {
    }

    @Override
    public @Nullable Integer apply(@Nullable Integer aggregate, @Nullable Integer next) {
        return next == null || aggregate != null && aggregate >= next ? aggregate : next;
    }
}
