package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.BiFunction;

public class BoundedMinAggregator implements BiFunction<Integer, Integer, Integer> {
    final public int minBound;

    public BoundedMinAggregator(final int minBound) {
        this.minBound = minBound;
    }

    @Override
    public Integer apply(final Integer aggregate, final Integer next) {
        if (next != null && next > minBound) return MinAggregator.INSTANCE.apply(aggregate, next);
        else return aggregate;
    }
}
