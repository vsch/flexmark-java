package com.vladsch.flexmark.core.test.util.renderer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ComboCommonMarkCompatibilitySpecTest.class,
  ComboMultiMarkdownCompatibilitySpecTest.class,
  ComboKramdownCompatibilitySpecTest.class,
  ComboMarkdownCompatibilitySpecTest.class,
  ComboGitHubCompatibilitySpecTest.class,
  ComboPegdownCompatibilitySpecTest.class,
})
public final class CoreCompatibilityTestSuite {}
