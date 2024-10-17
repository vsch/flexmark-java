package com.vladsch.flexmark.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.BiFunction;
import org.junit.Test;

public class MinAggregatorTest {
  private static Integer reduce(BiFunction<Integer, Integer, Integer> aggregator, int... items) {
    Integer aggregate = null;
    for (int item : items) {
      aggregate = aggregator.apply(aggregate, item);
    }
    return aggregate;
  }

  @Test
  public void test_Basic() {
    assertNull(reduce(MaxAggregator.INSTANCE));
    assertNull(reduce(MaxAggregator.INSTANCE, new int[0]));
    assertEquals((Integer) 1, reduce(MaxAggregator.INSTANCE, -1, -2, -5, 0, 1));
    assertEquals((Integer) 5, reduce(MaxAggregator.INSTANCE, -1, -2, -5, 0, 1, 5));
  }
}
