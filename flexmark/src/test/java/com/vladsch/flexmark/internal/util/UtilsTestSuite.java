package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.*;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegerBitSetTest.class,
        OrderedSetTest.class,
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        ClassificationBagTest.class,
})
public class UtilsTestSuite {
}
