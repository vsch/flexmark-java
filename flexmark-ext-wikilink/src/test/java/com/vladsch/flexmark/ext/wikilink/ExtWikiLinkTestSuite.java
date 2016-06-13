package com.vladsch.flexmark.ext.wikilink;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        WikiLinkFullSpecTest.class,
        WikiLinkSpecTest.class,
        WikiLinkCreoleFullSpecTest.class,
})
public class ExtWikiLinkTestSuite {
}
