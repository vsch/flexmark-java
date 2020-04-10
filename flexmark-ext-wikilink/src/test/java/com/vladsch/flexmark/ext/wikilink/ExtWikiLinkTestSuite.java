package com.vladsch.flexmark.ext.wikilink;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboWikiLinkSpecTest.class,
        ComboWikiLinkFormatterSpecTest.class,
        ComboWikiLinkTranslationFormatterSpecTest.class,
})
public class ExtWikiLinkTestSuite {
}
