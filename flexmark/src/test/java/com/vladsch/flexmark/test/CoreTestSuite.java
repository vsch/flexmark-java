package com.vladsch.flexmark.test;

import com.vladsch.flexmark.internal.util.UtilsTestSuite;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilsTestSuite.class,
        AbstractVisitorTest.class,
        DeilimitedNodeTest.class,
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
        UsageExampleTest.class
})
public class CoreTestSuite {
}
