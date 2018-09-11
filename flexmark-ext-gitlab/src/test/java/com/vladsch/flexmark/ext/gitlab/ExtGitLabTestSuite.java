package com.vladsch.flexmark.ext.gitlab;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboGitLabSpecTest.class,
        ComboGitLabFormatterSpecTest.class,
})
public class ExtGitLabTestSuite {
}
