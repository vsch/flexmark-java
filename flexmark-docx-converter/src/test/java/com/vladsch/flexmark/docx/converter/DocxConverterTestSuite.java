package com.vladsch.flexmark.docx.converter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboDocxConverterSpecTest.class,
        ComboDocxConverterAltStylesSpecTest.class,
        ComboEnDocxConverterSpecTest.class,
        ComboDeDocxConverterSpecTest.class,
        ComboDocxConverterSpec2Test.class,
        ComboEnDocxConverterSpec2Test.class,
        ComboDeDocxConverterSpec2Test.class,
        ComboDocxConverterIssuesSpecTest.class,
        ComboDocxConverterAttributeSpecTest.class,
        ComboDocxConverterFormSpecTest.class,
        //ComboDocxUserSpecDisabled.class, // added to test suite so it runs locally but not  on the travis
})
public class DocxConverterTestSuite {
}
