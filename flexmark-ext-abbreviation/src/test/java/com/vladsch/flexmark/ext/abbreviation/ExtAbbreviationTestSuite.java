package com.vladsch.flexmark.ext.abbreviation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboAbbreviationSpecTest.class,
        ComboAbbreviationFormatterSpecTest.class,
        MergeAbbreviationsTest.class,
})
public class ExtAbbreviationTestSuite {
}
