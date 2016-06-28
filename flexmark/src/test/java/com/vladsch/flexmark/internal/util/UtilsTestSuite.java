package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.*;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        CountingBitSetTest.class,
        OrderedSetTest.class,
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        ClassifiedBagTest.class,
})
public class UtilsTestSuite {
}
