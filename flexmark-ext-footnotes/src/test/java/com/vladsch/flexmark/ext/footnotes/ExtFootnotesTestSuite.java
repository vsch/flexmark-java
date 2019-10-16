package com.vladsch.flexmark.ext.footnotes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboFootnotesSpecTest.class,
        ComboFootnotesFormatterSpecTest.class,
        MergeFootnotesTest.class,
})
public class ExtFootnotesTestSuite {
}
