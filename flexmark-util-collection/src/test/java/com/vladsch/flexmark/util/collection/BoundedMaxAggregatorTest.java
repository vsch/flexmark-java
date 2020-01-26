package com.vladsch.flexmark.util.collection;

import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;

public class BoundedMaxAggregatorTest {

    private Integer reduce(BiFunction<Integer, Integer, Integer> aggregator, Integer... items) {
        Integer aggregate = null;
        for (Integer item : items) {
            aggregate = aggregator.apply(aggregate, item);
        }
        return aggregate;
    }

    @Test
    public void test_Basic() {
        assertEquals((Integer) null, reduce(new BoundedMaxAggregator(3)));
        assertEquals((Integer) null, reduce(new BoundedMaxAggregator(3), (Integer) null));
        assertEquals(2, (int) reduce(new BoundedMaxAggregator(3), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertEquals(4, (int) reduce(new BoundedMaxAggregator(5), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertEquals(null, reduce(new BoundedMaxAggregator(10), 10, 11, 12, 13));
    }
}
