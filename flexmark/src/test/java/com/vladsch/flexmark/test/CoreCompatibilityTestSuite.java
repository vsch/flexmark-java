package com.vladsch.flexmark.test;

import com.vladsch.flexmark.ast.util.TextCollectingVisitorTest;
import com.vladsch.flexmark.internal.HtmlDeepParserTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
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
