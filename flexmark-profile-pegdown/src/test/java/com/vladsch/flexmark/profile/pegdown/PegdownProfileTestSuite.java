package com.vladsch.flexmark.profile.pegdown;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
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
