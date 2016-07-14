package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.*;
import com.vladsch.flexmark.internal.util.options.AttributeTest;
import com.vladsch.flexmark.internal.util.options.AttributesTest;
import com.vladsch.flexmark.internal.util.sequence.BasedSequenceImplTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegerBitSetTest.class,
        OrderedSetTest.class,
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        ClassificationBagTest.class,
        BasedSequenceImplTest.class,
        AttributeTest.class,
        AttributesTest.class,
})
public class UtilsTestSuite {
}
