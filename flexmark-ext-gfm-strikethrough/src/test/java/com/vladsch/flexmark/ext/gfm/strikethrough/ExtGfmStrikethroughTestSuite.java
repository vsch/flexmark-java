package com.vladsch.flexmark.ext.gfm.strikethrough;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
    StrikethroughTest.class,
        StrikethroughTest.class,
        ComboStrikethroughSpecTest.class,
        ComboSubscriptSpecTest.class,
        ComboStrikethroughSubscriptSpecTest.class,
        //JiraStrikethroughSpecTest.class,
        //YouTrackStrikethroughSpecTest.class,
})
public class ExtGfmStrikethroughTestSuite {
}
