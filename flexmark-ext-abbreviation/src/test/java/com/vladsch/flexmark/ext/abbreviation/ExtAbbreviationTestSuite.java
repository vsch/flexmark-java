package com.vladsch.flexmark.ext.abbreviation;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboAbbreviationSpecTest.class,
        ComboAbbreviationFormatterSpecTest.class,
        MergeAbbreviationsTest.class,
})
public class ExtAbbreviationTestSuite {
}
