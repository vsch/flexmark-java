package com.vladsch.flexmark.test;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        AbstractVisitorTest.class,
        DelimitedNodeTest.class,
        DelimiterProcessorTest.class,
        HtmlRendererTest.class,
        ParserTest.class,
        PathologicalTest.class,
        PrefixedSubSequenceTest.class,
        FullOrigSpecCoreTest.class,
        ComboCoreSpecTest.class,
        ComboExtraSpecTest.class,
        SpecialInputTest.class,
        UsageExampleTest.class,
        ComboCommonMarkCompatibilitySpecTest.class,
        ComboMultiMarkdownCompatibilitySpecTest.class,
        ComboKramdownCompatibilitySpecTest.class,
        ComboMarkdownCompatibilitySpecTest.class,
        ComboGitHubCompatibilitySpecTest.class,
        ComboPegdownCompatibilitySpecTest.class,
        ComboIssuesSpecTest.class,
})
public class CoreTestSuite {
}
