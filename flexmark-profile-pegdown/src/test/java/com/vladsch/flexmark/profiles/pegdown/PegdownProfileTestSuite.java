package com.vladsch.flexmark.profiles.pegdown;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboPegdownSpecTest.class,
        ComboPegdownCompatibilitySpecTest.class,
        ComboPegdownExtensionCompatibilitySpecTest.class,
        ComboPegdownDoxiaCompatibilitySpecTest.class,
        ComboStackOverflowSpecTest.class,
        ComboIssueMn236ExceptionSpecTest.class,
})
public class PegdownProfileTestSuite {
}
