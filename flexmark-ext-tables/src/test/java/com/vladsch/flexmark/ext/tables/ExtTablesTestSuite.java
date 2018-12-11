package com.vladsch.flexmark.ext.tables;

import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboTableSpecTest.class,
        ComboTableFormatterSpecTest.class,
        ComboTableManipulationSpecTest.class,
        TableTextCollectingVisitorTest.class,
        MarkdownTableTest.class,
        TableCellOffsetInfoTest.class,
        //JiraTablesSpecTest.class,
})
public class ExtTablesTestSuite {
}
