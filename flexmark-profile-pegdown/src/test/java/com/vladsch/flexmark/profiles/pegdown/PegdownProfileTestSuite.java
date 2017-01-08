package com.vladsch.flexmark.profiles.pegdown;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboPegdownSpecTest.class,
        ComboPegdownCompatibilitySpecTest.class,
        ComboPegdownExtSpecTest.class,
        ComboPegdownExtCompatibilitySpecTest.class,
})
public class PegdownProfileTestSuite {
}
