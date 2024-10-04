package com.vladsch.flexmark.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.BiFunction;
import org.junit.Test;

public class BoundedMinAggregatorTest {
  private static Integer reduce(
      BiFunction<Integer, Integer, Integer> aggregator, Integer... items) {
    Integer aggregate = null;
    for (Integer item : items) {
      aggregate = aggregator.apply(aggregate, item);
    }
    return aggregate;
  }

  @Test
  public void test_Basic() {
    assertNull(reduce(new BoundedMinAggregator(3)));
    assertNull(reduce(new BoundedMinAggregator(3), (Integer) null));
    assertEquals(4, (int) reduce(new BoundedMinAggregator(3), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    assertEquals(6, (int) reduce(new BoundedMinAggregator(5), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    assertNull(reduce(new BoundedMinAggregator(10), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  }
}
