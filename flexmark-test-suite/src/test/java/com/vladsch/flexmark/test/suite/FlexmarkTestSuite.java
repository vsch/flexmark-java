package com.vladsch.flexmark.test.suite;

import com.vladsch.flexmark.docx.converter.DocxConverterTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FlexmarkTestSuiteNoDocx.class,
        DocxConverterTestSuite.class,
})
public class FlexmarkTestSuite {
}
