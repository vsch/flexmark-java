package com.vladsch.flexmark.ext.wikilink;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboWikiLinkSpecTest.class,
        JiraWikiLinkSpecTest.class,
        //WikiLinkFullSpecTest.class,
        //WikiLinkSpecTest.class,
        //WikiLinkCreoleFullSpecTest.class,
})
public class ExtWikiLinkTestSuite {
}
