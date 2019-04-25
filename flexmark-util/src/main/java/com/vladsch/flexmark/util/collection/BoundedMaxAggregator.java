package com.vladsch.flexmark.util.collection;

import java.util.function.BiFunction;

public class BoundedMaxAggregator implements BiFunction<Integer, Integer, Integer> {
    final public int maxBound;

    public BoundedMaxAggregator(int maxBound) {
        this.maxBound = maxBound;
    }

    @Override
    public Integer apply(Integer aggregate, Integer next) {
        if (next != null && next < maxBound) return MaxAggregator.INSTANCE.apply(aggregate, next);
        else return aggregate;
    }
}
