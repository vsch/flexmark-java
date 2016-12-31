package com.vladsch.flexmark.ext.emoji;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboEmojiSpecTest.class,
        ComboEmojiJiraTest.class,
        ComboEmojiCrashSpecTest.class,
})
public class ExtEmojiTestSuite {
}
