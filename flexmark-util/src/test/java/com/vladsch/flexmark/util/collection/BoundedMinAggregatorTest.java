package com.vladsch.flexmark.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.BinaryOperator;
import org.junit.Test;

public class BoundedMinAggregatorTest {
  private static Integer reduce(BinaryOperator<Integer> aggregator, int... items) {
    Integer aggregate = null;
    for (int item : items) {
      aggregate = aggregator.apply(aggregate, item);
    }
    return aggregate;
  }

  @Test
  public void test_Basic() {
    assertNull(reduce(new BoundedMinAggregator(3)));
    assertNull(reduce(new BoundedMinAggregator(3), new int[0]));
    assertEquals(4, reduce(new BoundedMinAggregator(3), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).intValue());
    assertEquals(6, reduce(new BoundedMinAggregator(5), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).intValue());
    assertNull(reduce(new BoundedMinAggregator(10), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  }
}
