package com.vladsch.flexmark.convert.html;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboFlexmarkHtmlParserTest.class,
        ComboFlexmarkHtmlAttributesParserTest.class,
        ComboAppHtmlParserTest.class,
        ComboAppHtmlAttributeParserTest.class,
        ComboHtmlParserIssueTest.class,
        ComboHtmlAttributeParserIssueTest.class,
})
public class FlexmarkHtmlParserTestSuite {
}
