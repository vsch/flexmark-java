package com.vladsch.flexmark.test;

import com.vladsch.flexmark.ast.util.TextCollectingVisitorTest;
import com.vladsch.flexmark.internal.HtmlDeepParserTest;
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
        FullOrigSpec027CoreTest.class,
        FullOrigSpec028CoreTest.class,
        ComboCoreSpecTest.class,
        ComboCoreDirectionalSpecTest.class,
        ComboExtraSpecTest.class,
        SpecialInputTest.class,
        UsageExampleTest.class,
        CoreCompatibilityTestSuite.class,
        ComboIssuesSpecTest.class,
        TextCollectingVisitorTest.class,
        HtmlDeepParserTest.class,
})
public class CoreTestSuite {
}
