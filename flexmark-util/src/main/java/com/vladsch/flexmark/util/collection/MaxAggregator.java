package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.BiFunction;
import com.vladsch.flexmark.util.format.MarkdownTable;

public class MaxAggregator implements BiFunction<Integer, Integer, Integer> {
    final public static MaxAggregator INSTANCE = new MaxAggregator();

    private MaxAggregator() {
    }

    @Override
    public Integer apply(final Integer aggregate, final Integer next) {
        return next == null || aggregate != null && aggregate >= next ? aggregate : next;
    }
}
