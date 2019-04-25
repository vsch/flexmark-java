package com.vladsch.flexmark.util.collection;

import java.util.function.BiFunction;

public class MaxAggregator implements BiFunction<Integer, Integer, Integer> {
    final public static MaxAggregator INSTANCE = new MaxAggregator();

    private MaxAggregator() {
    }

    @Override
    public Integer apply(Integer aggregate, Integer next) {
        return next == null || aggregate != null && aggregate >= next ? aggregate : next;
    }
}
