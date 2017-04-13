package com.vladsch.flexmark.test;

import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ast.util.TextCollectingVisitorTest;
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
        TextCollectingVisitorTest.class,
})
public class CoreTestSuite {
}
