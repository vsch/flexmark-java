package com.vladsch.flexmark.ext.admonition;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboAdmonitionSpecTest.class,
        ComboAdmonitionFormatterSpecTest.class,
        ComboAdmonitionTranslationFormatterSpecTest.class,
})
public class ExtAdmonitionTestSuite {
}
