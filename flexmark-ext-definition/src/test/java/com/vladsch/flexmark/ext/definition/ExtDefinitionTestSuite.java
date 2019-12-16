package com.vladsch.flexmark.ext.definition;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboDefinitionSpecTest.class,
        ComboDefinitionFormatterSpecTest.class,
        DefinitionParserTest.class,
})
public class ExtDefinitionTestSuite {
}
