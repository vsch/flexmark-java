package com.vladsch.flexmark.ext.admonition;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboAdmonitionSpecTest.class,
        ComboAdmonitionFormatterSpecTest.class,
        ComboAdmonitionTranslationFormatterSpecTest.class,
        AdmonitionParserTest.class,
})
public class ExtAdmonitionTestSuite {
}
