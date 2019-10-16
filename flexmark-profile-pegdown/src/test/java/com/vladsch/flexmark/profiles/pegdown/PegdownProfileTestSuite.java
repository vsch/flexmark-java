package com.vladsch.flexmark.profiles.pegdown;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboPegdownSpecTest.class,
        ComboPegdownCompatibilitySpecTest.class,
        ComboPegdownExtensionCompatibilitySpecTest.class,
        ComboStackOverflowSpecTest.class,
        ComboIssueMn236ExceptionSpecTest.class,
})
public class PegdownProfileTestSuite {
}
