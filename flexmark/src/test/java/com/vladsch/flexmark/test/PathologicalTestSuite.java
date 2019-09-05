package com.vladsch.flexmark.test;

import com.vladsch.flexmark.ast.util.TextCollectingVisitorTest;
import com.vladsch.flexmark.parser.internal.HtmlDeepParserTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        PathologicalTest.class,
        PathologicalSpcUrlTest.class,
})
public class PathologicalTestSuite {
}
