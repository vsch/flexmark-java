package com.vladsch.flexmark.integration;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        BoundsIntegrationTest.class,
        SpecIntegrationTest.class,
        ComboParserTest.class,
})
public class IntegrationTestSuite {
}
