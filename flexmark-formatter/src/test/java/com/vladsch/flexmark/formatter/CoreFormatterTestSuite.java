package com.vladsch.flexmark.formatter;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboCoreFormatterSpecTest.class,
        ComboCoreTranslationFormatterSpecTest.class,
        ComboCoreFormatterNoBlankLinesSpecTest.class,
        FormatterModifiedAST.class,
})
public class CoreFormatterTestSuite {
}
