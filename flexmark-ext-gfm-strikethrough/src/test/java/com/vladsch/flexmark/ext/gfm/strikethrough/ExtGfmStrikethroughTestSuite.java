package com.vladsch.flexmark.ext.gfm.strikethrough;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
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
