package com.vladsch.flexmark.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.BinaryOperator;
import org.junit.Test;

public class MaxAggregatorTest {
  private static Integer reduce(BinaryOperator<Integer> aggregator, int... items) {
    Integer aggregate = null;
    for (int item : items) {
      aggregate = aggregator.apply(aggregate, item);
    }
    return aggregate;
  }

  @Test
  public void test_Basic() {
    assertNull(reduce(MinAggregator.INSTANCE));
    assertNull(reduce(MinAggregator.INSTANCE, new int[0]));
    assertEquals(-5, reduce(MinAggregator.INSTANCE, -1, -2, -5, 0, 1).intValue());
    assertEquals(-5, reduce(MinAggregator.INSTANCE, -1, -2, -5, 0, 1, 5).intValue());
  }
}
