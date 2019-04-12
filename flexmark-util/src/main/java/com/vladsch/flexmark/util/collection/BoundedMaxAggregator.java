package com.vladsch.flexmark.util.collection;

import java.util.function.BiFunction;

public class BoundedMaxAggregator implements BiFunction<Integer, Integer, Integer> {
    final public int maxBound;

    public BoundedMaxAggregator(final int maxBound) {
        this.maxBound = maxBound;
    }

    @Override
    public Integer apply(final Integer aggregate, final Integer next) {
        if (next != null && next < maxBound) return MaxAggregator.INSTANCE.apply(aggregate, next);
        else return aggregate;
    }
}
