package com.vladsch.flexmark.ext.toc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TocOptionsParserTest.class,
        ComboTocSpecTest.class,
        ComboSimTocSpecTest.class,
})
public class ExtTocTestSuite {
}
