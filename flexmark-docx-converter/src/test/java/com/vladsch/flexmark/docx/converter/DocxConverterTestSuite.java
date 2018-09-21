package com.vladsch.flexmark.docx.converter;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboDocxConverterSpecTest.class,
        ComboEnDocxConverterSpecTest.class,
        ComboDeDocxConverterSpecTest.class,
        ComboDocxConverterIssuesSpecTest.class,
        //ComboDocxUserSpecDisabled.class, // added to test suite so it runs locally but not  on the travis
})
public class DocxConverterTestSuite {
}
