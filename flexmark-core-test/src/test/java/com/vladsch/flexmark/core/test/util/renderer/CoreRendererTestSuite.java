package com.vladsch.flexmark.core.test.util.renderer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboExtraSpecTest.class,
        ComboExtraSpec2Test.class,
        CoreCompatibilityTestSuite.class,
        ComboIssuesSpecTest.class,
        FullOrigSpecCoreTest.class,
        FullOrigSpec027CoreTest.class,
        FullOrigSpec028CoreTest.class,
        FullOrigSpec029CoreTest.class,
        ComboCoreSpecTest.class,
        ComboCoreDirectionalSpecTest.class,
})
public class CoreRendererTestSuite {
}
