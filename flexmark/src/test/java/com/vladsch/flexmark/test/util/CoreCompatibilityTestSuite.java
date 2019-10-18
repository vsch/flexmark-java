package com.vladsch.flexmark.test.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboCommonMarkCompatibilitySpecTest.class,
        ComboMultiMarkdownCompatibilitySpecTest.class,
        ComboKramdownCompatibilitySpecTest.class,
        ComboMarkdownCompatibilitySpecTest.class,
        ComboGitHubCompatibilitySpecTest.class,
        ComboPegdownCompatibilitySpecTest.class,
})
public class CoreCompatibilityTestSuite {
}
