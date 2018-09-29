package com.vladsch.flexmark.ext.macros;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboMacrosSpecTest.class,
        ComboMacrosFormatterSpecTest.class,
        ComboMacrosTranslationFormatterSpecTest.class,
})
public class ExtMacrosTestSuite {
}
