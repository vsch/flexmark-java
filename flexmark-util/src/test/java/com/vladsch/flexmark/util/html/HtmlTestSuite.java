package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.html.ui.HtmlBuilderTest;
import com.vladsch.flexmark.util.html.ui.HtmlHelpersTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HtmlBuilderTest.class,
        HtmlAppendableBaseTest.class,
        HtmlHelpersTest.class,
})
public class HtmlTestSuite {
}
