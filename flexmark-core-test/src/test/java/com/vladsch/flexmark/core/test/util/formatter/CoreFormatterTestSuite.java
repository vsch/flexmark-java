package com.vladsch.flexmark.core.test.util.formatter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboCoreFormatterSpecTest.class,
        ComboCoreTranslationFormatterSpecTest.class,
        ComboCoreFormatterNoBlankLinesSpecTest.class,
        FormatterModifiedAST.class,
        MergeFormatterCoreTest.class,
})
public class CoreFormatterTestSuite {
}
