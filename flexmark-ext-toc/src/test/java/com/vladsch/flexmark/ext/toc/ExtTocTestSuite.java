package com.vladsch.flexmark.ext.toc;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        TocOptionsParserTest.class,
        ComboTocSpecTest.class,
        ComboSimTocSpecTest.class,
})
public class ExtTocTestSuite {
}
