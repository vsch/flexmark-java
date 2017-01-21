package com.vladsch.flexmark.profiles.pegdown;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboPegdownSpecTest.class,
        ComboPegdownCompatibilitySpecTest.class,
        ComboPegdownExtensionCompatibilitySpecTest.class,
})
public class PegdownProfileTestSuite {
}
