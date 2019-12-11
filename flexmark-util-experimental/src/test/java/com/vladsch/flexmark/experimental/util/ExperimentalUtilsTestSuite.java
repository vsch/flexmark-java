package com.vladsch.flexmark.experimental.util;

import com.vladsch.flexmark.experimental.util.collection.BitIntegerSetTest;
import com.vladsch.flexmark.experimental.util.collection.IntegerBitSetTest;
import com.vladsch.flexmark.experimental.util.collection.iteration.PositionListTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegerBitSetTest.class,
        BitIntegerSetTest.class,
        PositionListTest.class,
})
public class ExperimentalUtilsTestSuite {
}
