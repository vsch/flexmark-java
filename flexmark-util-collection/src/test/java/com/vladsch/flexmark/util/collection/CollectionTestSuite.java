package com.vladsch.flexmark.util.collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        OrderedSetTest.class,
        ClassificationBagTest.class,
        MaxAggregatorTest.class,
        MinAggregatorTest.class,
        BoundedMaxAggregatorTest.class,
        BoundedMinAggregatorTest.class,
})
public class CollectionTestSuite {
}
