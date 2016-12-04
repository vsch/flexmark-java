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
        SubstringTest.class,
        UsageExampleTest.class,
        ComboParserCompatibilitySpecTest.class,
})
public class CoreTestSuite {
}
