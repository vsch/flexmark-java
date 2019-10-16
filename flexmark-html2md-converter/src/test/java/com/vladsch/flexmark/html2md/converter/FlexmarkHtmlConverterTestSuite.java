package com.vladsch.flexmark.html2md.converter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboFlexmarkHtmlConverterTest.class,
        ComboFlexmarkHtmlAttributesConverterTest.class,
        ComboAppHtmlConverterTest.class,
        ComboAppHtmlAttributeConverterTest.class,
        ComboHtmlConverterIssueTest.class,
        ComboHtmlAttributeConverterIssueTest.class,
})
public class FlexmarkHtmlConverterTestSuite {
}
