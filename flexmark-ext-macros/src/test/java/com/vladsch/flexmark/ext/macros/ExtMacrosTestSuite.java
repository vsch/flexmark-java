package com.vladsch.flexmark.ext.macros;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboMacrosSpecTest.class,
        ComboMacrosFormatterSpecTest.class,
        ComboMacrosTranslationFormatterSpecTest.class,
        MergeMacrosTest.class,
})
public class ExtMacrosTestSuite {
}
