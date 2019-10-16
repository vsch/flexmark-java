package com.vladsch.flexmark.ext.emoji;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboEmojiSpecTest.class,
        ComboEmojiJiraTest.class,
        ComboEmojiCrashSpecTest.class,
})
public class ExtEmojiTestSuite {
}
