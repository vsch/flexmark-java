package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.ClassifiedBagTest;
import com.vladsch.flexmark.internal.util.collection.CountingBitSetTest;
import com.vladsch.flexmark.internal.util.collection.OrderedMapTest;
import com.vladsch.flexmark.internal.util.collection.OrderedSetTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        CountingBitSetTest.class,
        OrderedSetTest.class,
        OrderedMapTest.class,
        ClassifiedBagTest.class,
})
public class UtilsTestSuite {
}
