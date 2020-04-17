package com.vladsch.flexmark.ext.jekyll.tag;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboJekyllTagSpecTest.class,
        ComboJekyllTagFormatterSpecTest.class,
        MergeJekyllTagTest.class,
})
public class ExtJekyllTagTestSuite {
}
