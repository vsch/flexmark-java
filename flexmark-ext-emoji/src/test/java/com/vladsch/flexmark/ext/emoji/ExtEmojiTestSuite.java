package com.vladsch.flexmark.ext.emoji;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
    EmojiFullSpecTest.class,
    EmojiSpecTest.class,
    EmojiUrlFullSpecTest.class,
    EmojiUrlSpecTest.class
})
public class ExtEmojiTestSuite {
}
