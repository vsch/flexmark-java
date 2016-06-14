package com.vladsch.flexmark.ext.footnotes;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
    FootnotesFullSpecTest.class,
    FootnotesSpecTest.class,
        FootnoteOptionsTest.class,
})
public class ExtFootnotesTestSuite {
}
