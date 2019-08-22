package com.vladsch.flexmark.ext.footnotes;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboFootnotesSpecTest.class,
        ComboFootnotesFormatterSpecTest.class,
        MergeFootnotesTest.class,
})
public class ExtFootnotesTestSuite {
}
