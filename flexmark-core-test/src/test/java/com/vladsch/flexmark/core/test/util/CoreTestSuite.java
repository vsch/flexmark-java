package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.core.test.util.html.HtmlEmbeddedAttributeTest;
import com.vladsch.flexmark.core.test.util.html.HtmlRendererTest;
import com.vladsch.flexmark.core.test.util.html.PathologicalTestSuite;
import com.vladsch.flexmark.core.test.util.parser.DelimiterProcessorTest;
import com.vladsch.flexmark.core.test.util.parser.HtmlDeepParserTest;
import com.vladsch.flexmark.core.test.util.parser.LinkDestinationParserTest;
import com.vladsch.flexmark.core.test.util.parser.ParserTest;
import com.vladsch.flexmark.core.test.util.parser.SpecialInputTest;
import com.vladsch.flexmark.core.test.util.parser.UsageExampleTest;
import com.vladsch.flexmark.core.test.util.parser.ast.AbstractVisitorTest;
import com.vladsch.flexmark.core.test.util.parser.ast.DelimitedNodeTest;
import com.vladsch.flexmark.core.test.util.parser.ast.TextCollectingVisitorTest;
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
  SpecialInputTest.class,
  UsageExampleTest.class,
  TextCollectingVisitorTest.class,
  HtmlDeepParserTest.class,
  HtmlEmbeddedAttributeTest.class,
})
public final class CoreTestSuite {}
