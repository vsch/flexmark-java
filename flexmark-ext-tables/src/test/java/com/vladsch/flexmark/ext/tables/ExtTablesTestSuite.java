package com.vladsch.flexmark.ext.tables;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboTableSpecTest.class,
        ComboTableFormatterSpecTest.class,
        ComboTableManipulationSpecTest.class,
        TableTextCollectingVisitorTest.class,
        MarkdownTableTest.class,
        MarkdownTransposeTableTest.class,
        MarkdownSortTableTest.class,
        TableCellOffsetInfoTest.class,
        //JiraTablesSpecTest.class,
})
public class ExtTablesTestSuite {
}
