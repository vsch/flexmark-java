package com.vladsch.flexmark.ext.gitlab;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboGitLabSpecTest.class,
        ComboGitLabFormatterSpecTest.class,
})
public class ExtGitLabTestSuite {
}
