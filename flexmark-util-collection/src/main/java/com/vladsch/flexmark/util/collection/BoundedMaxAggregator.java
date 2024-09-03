package com.vladsch.flexmark.util.collection;

import java.util.function.BiFunction;
import org.jetbrains.annotations.Nullable;

public class BoundedMaxAggregator implements BiFunction<Integer, Integer, Integer> {
    final public int maxBound;

    public BoundedMaxAggregator(int maxBound) {
        this.maxBound = maxBound;
    }

    @Override
    public Integer apply(@Nullable Integer aggregate, @Nullable Integer next) {
        if (next != null && next < maxBound) return MaxAggregator.INSTANCE.apply(aggregate, next);
        else return aggregate;
    }
}
