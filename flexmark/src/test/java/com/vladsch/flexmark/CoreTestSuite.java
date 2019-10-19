package com.vladsch.flexmark;

import com.vladsch.flexmark.ast.DelimitedNodeTest;
import com.vladsch.flexmark.ast.util.TextCollectingVisitorTest;
import com.vladsch.flexmark.html.HtmlEmbeddedAttributeTest;
import com.vladsch.flexmark.html.HtmlRendererTest;
import com.vladsch.flexmark.html.PathologicalTestSuite;
import com.vladsch.flexmark.parser.ParserTest;
import com.vladsch.flexmark.parser.SpecialInputTest;
import com.vladsch.flexmark.parser.UsageExampleTest;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessorTest;
import com.vladsch.flexmark.parser.internal.HtmlDeepParserTest;
import com.vladsch.flexmark.parser.internal.LinkDestinationParserTest;
import com.vladsch.flexmark.ast.AbstractVisitorTest;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequenceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AbstractVisitorTest.class,
        DelimitedNodeTest.class,
        DelimiterProcessorTest.class,
        HtmlRendererTest.class,
        ParserTest.class,
        LinkDestinationParserTest.class,
        PathologicalTestSuite.class,
        PrefixedSubSequenceTest.class,
        SpecialInputTest.class,
        UsageExampleTest.class,
        TextCollectingVisitorTest.class,
        HtmlDeepParserTest.class,
        HtmlEmbeddedAttributeTest.class,
})
final public class CoreTestSuite {
}
