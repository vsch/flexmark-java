package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.formatter.internal.MarkdownWriter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarkdownTableTest {

    private MarkdownTable[] getTables(String markdown) {
        return getTables(markdown, null);
    }

    private MarkdownTable[] getTables(String markdown, DataHolder options) {
        if (options == null) {
            options = new MutableDataSet()
                    .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
        }
        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        TableExtractingVisitor tableVisitor = new TableExtractingVisitor(options);
        return tableVisitor.getTables(document);
    }

    private String getFormattedTable(MarkdownTable table) {
        return getFormattedTable(table, "", "");
    }

    private String getFormattedTable(MarkdownTable table, String linePrefix, String intellijDummyIdentifier) {
        MarkdownWriter out = new MarkdownWriter(new StringBuilder(), MarkdownWriter.FORMAT_ALL);
        table.finalizeTable();
        table.appendTable(out);
        out.flush();
        return out.toString();
    }

    private String[] getFormattedTables(MarkdownTable[] tables) {
        List<String> formatted = new ArrayList<>();

        for (MarkdownTable table : tables) {
            MarkdownWriter out = new MarkdownWriter(new StringBuilder(), MarkdownWriter.FORMAT_ALL);
            table.finalizeTable();
            table.appendTable(out);
            out.flush();
            formatted.add(out.toString());
        }
        return formatted.toArray(new String[0]);
    }

    private MarkdownTable getTable(String markdown) {
        MarkdownTable table = getTables(markdown)[0];
        table.normalize();
        return table;
    }

    final static private String markdown1 = "" +
            "| First Header  |\n" +
            "| ------------- |\n" +
            "| Content Cell  |\n" +
            "\n" +
            "";
    final static private String markdown2 = "" +
            "| Left-aligned | Right-aligned1 |\n" +
            "| Left-aligned | Right-aligned2 |\n" +
            "| :---         |          ---: |\n" +
            "| git status   | git status1    |\n" +
            "| git diff     | git diff2      |\n" +
            "[  ]\n" +
            "";
    final static private String markdown3 =
            "| Left-aligned | Center-aligned | Right-aligned1 |\n" +
                    "| Left-aligned | Center-aligned | Right-aligned2 |\n" +
                    "| Left-aligned | Center-aligned | Right-aligned3 |\n" +
                    "| :---         |     :---:      |          ---: |\n" +
                    "| git status   | git status     | git status1    |\n" +
                    "| git diff     | git diff       | git diff2      |\n" +
                    "| git diff     | git diff       | git diff3      |\n" +
                    "[Table Caption]\n" +
                    "";

    final static private String markdown4 = "" +
            "| Left-aligned1 |\n" +
            "| Left-aligned | Center-aligned2 |\n" +
            "| Left-aligned | Center-aligned | Right-aligned3 |\n" +
            "| Left-aligned | Center-aligned | Right-aligned | Right-aligned4 |\n" +
            "| :---         |     :---:      |          ---: |          ---: |\n" +
            "| git status1  |\n" +
            "| git diff     | git diff2      |\n" +
            "| git diff     | git diff       | git diff3      |\n" +
            "| git diff     | git diff       | git diff       | git diff4      |\n" +
            "[Table Caption]\n" +
            "";

    final static private String markdown5 = "" +
            "| Left-aligned1 |\n" +
            "| Left-aligned | Center-aligned2 |\n" +
            "| Left-aligned | Center-aligned | Right-aligned3 |\n" +
            "| Left-aligned | Center-aligned | Right-aligned | Right-aligned4 |\n" +
            "|  |  |  |  |\n" +
            "| :---         |     :---:      |          ---: |          ---: |\n" +
            "| git status1  |\n" +
            "| git diff     | git diff2      |\n" +
            "| git diff     | git diff       | git diff3      |\n" +
            "| git diff     | git diff       | git diff       | git diff4      |\n" +
            "|  |  |  |  |\n" +
            "[Table Caption]\n" +
            "";

    final static private String formattedNoCaption1 = "" +
            "| First Header |\n" +
            "|--------------|\n" +
            "| Content Cell |\n" +
            "";

    final static private String formattedNoCaption2 = "" +
            "| Left-aligned | Right-aligned1 |\n" +
            "| Left-aligned | Right-aligned2 |\n" +
            "|:-------------|---------------:|\n" +
            "| git status   |    git status1 |\n" +
            "| git diff     |      git diff2 |\n" +
            "";
    final static private String formattedNoCaption3 = "" +
            "| Left-aligned | Center-aligned | Right-aligned1 |\n" +
            "| Left-aligned | Center-aligned | Right-aligned2 |\n" +
            "| Left-aligned | Center-aligned | Right-aligned3 |\n" +
            "|:-------------|:--------------:|---------------:|\n" +
            "| git status   |   git status   |    git status1 |\n" +
            "| git diff     |    git diff    |      git diff2 |\n" +
            "| git diff     |    git diff    |      git diff3 |\n" +
            "";

    final static private String formatted1 = formattedNoCaption1 +
            "";

    final static private String formatted2 = formattedNoCaption2 +
            "[ ]\n" +
            "";

    final static private String formatted3 = formattedNoCaption3 +
            "[Table Caption]\n" +
            "";

    @Test
    public void test_basic() {
        String[] markdowns = new String[] { markdown1, markdown2, markdown3 };
        MarkdownTable[] tables = getTables(markdown1 + markdown2 + markdown3);
        String[] formattedTables = getFormattedTables(tables);
        String[] expected = new String[] { formatted1, formatted2, formatted3 };

        int iMax = tables.length;
        for (int i = iMax; i-- > 0; ) {
            assertEquals("Table " + (i + 1), expected[i], formattedTables[i]);
        }
    }

    @Test
    public void test_getCaption() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);
        assertEquals("", table1.getCaption().toString());
        assertEquals("  ", table2.getCaption().toString());
        assertEquals("Table Caption", table3.getCaption().toString());

        assertEquals("", table1.getCaptionOpen().toString());
        assertEquals("[", table2.getCaptionOpen().toString());
        assertEquals("[", table3.getCaptionOpen().toString());

        assertEquals("", table1.getCaptionClose().toString());
        assertEquals("]", table2.getCaptionClose().toString());
        assertEquals("]", table3.getCaptionClose().toString());
    }

    @Test
    public void test_setCaption() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        table1.setCaption("Table 1");
        table2.setCaption("Table 2");
        table3.setCaption("Table 3");

        assertEquals("Table 1", table1.getCaption().toString());
        assertEquals("Table 2", table2.getCaption().toString());
        assertEquals("Table 3", table3.getCaption().toString());

        assertEquals(formattedNoCaption1 + "[Table 1]\n", getFormattedTable(table1).toString());
        assertEquals(formattedNoCaption2 + "[Table 2]\n", getFormattedTable(table2).toString());
        assertEquals(formattedNoCaption3 + "[Table 3]\n", getFormattedTable(table3).toString());
    }

    @Test
    public void test_setCaptionWithMarkers() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        table1.setCaptionWithMarkers("[", "Table 1", "]");
        table2.setCaptionWithMarkers("[", "Table 2", "]");
        table3.setCaptionWithMarkers("[", "Table 3", "]");

        assertEquals("Table 1", table1.getCaption().toString());
        assertEquals("Table 2", table2.getCaption().toString());
        assertEquals("Table 3", table3.getCaption().toString());

        assertEquals(formattedNoCaption1 + "[Table 1]\n", getFormattedTable(table1).toString());
        assertEquals(formattedNoCaption2 + "[Table 2]\n", getFormattedTable(table2).toString());
        assertEquals(formattedNoCaption3 + "[Table 3]\n", getFormattedTable(table3).toString());
    }

    @Test
    public void test_getHeadingRowCount() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getHeadingRowCount());
        assertEquals(2, table2.getHeadingRowCount());
        assertEquals(3, table3.getHeadingRowCount());
    }

    @Test
    public void test_getBodyRowCount() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getBodyRowCount());
        assertEquals(2, table2.getBodyRowCount());
        assertEquals(3, table3.getBodyRowCount());
    }

    @Test
    public void test_getMaxHeadingColumns() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getMaxHeadingColumns());
        assertEquals(2, table2.getMaxHeadingColumns());
        assertEquals(3, table3.getMaxHeadingColumns());

        MarkdownTable table4 = getTable(markdown4);
        assertEquals(4, table4.getMaxHeadingColumns());
    }

    @Test
    public void test_getMaxSeparatorColumns() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getMaxSeparatorColumns());
        assertEquals(2, table2.getMaxSeparatorColumns());
        assertEquals(3, table3.getMaxSeparatorColumns());

        MarkdownTable table4 = getTable(markdown4);
        assertEquals(4, table4.getMaxSeparatorColumns());
    }

    @Test
    public void test_getMaxBodyColumns() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getMaxBodyColumns());
        assertEquals(2, table2.getMaxBodyColumns());
        assertEquals(3, table3.getMaxBodyColumns());

        MarkdownTable table4 = getTable(markdown4);
        assertEquals(4, table4.getMaxBodyColumns());
    }

    @Test
    public void test_getMinColumns() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getMinColumns());
        assertEquals(2, table2.getMinColumns());
        assertEquals(3, table3.getMinColumns());

        MarkdownTable table4 = getTable(markdown4);
        assertEquals(1, table4.getMinColumns());
    }

    @Test
    public void test_getMaxColumns() {
        MarkdownTable table1 = getTable(markdown1);
        MarkdownTable table2 = getTable(markdown2);
        MarkdownTable table3 = getTable(markdown3);

        assertEquals(1, table1.getMaxColumns());
        assertEquals(2, table2.getMaxColumns());
        assertEquals(3, table3.getMaxColumns());

        MarkdownTable table4 = getTable(markdown4);
        assertEquals(4, table4.getMaxColumns());
    }

    @Test
    public void test_maxColumnsWithout() {
        MarkdownTable table4 = getTable(markdown4);
        // @formatter:off
        assertEquals(0, table4.getMaxColumnsWithout(true, 0, 1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(1, table4.getMaxColumnsWithout(true,    1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(2, table4.getMaxColumnsWithout(true, 0,    2, 3, 4, 5, 6, 7, 8));
        assertEquals(3, table4.getMaxColumnsWithout(true, 0, 1,    3, 4, 5, 6, 7, 8));
        assertEquals(4, table4.getMaxColumnsWithout(true, 0, 1, 2,    4, 5, 6, 7, 8));
        assertEquals(4, table4.getMaxColumnsWithout(true, 0, 1, 2, 3,    5, 6, 7, 8));
        assertEquals(1, table4.getMaxColumnsWithout(true, 0, 1, 2, 3, 4,    6, 7, 8));
        assertEquals(2, table4.getMaxColumnsWithout(true, 0, 1, 2, 3, 4, 5,    7, 8));
        assertEquals(3, table4.getMaxColumnsWithout(true, 0, 1, 2, 3, 4, 5, 6,    8));
        assertEquals(4, table4.getMaxColumnsWithout(true, 0, 1, 2, 3, 4, 5, 6, 7   ));
   
        assertEquals(0, table4.getMaxColumnsWithout(false, 0, 1, 2, 3, 4, 5, 6, 7));
        assertEquals(1, table4.getMaxColumnsWithout(false,    1, 2, 3, 4, 5, 6, 7));
        assertEquals(2, table4.getMaxColumnsWithout(false, 0,    2, 3, 4, 5, 6, 7));
        assertEquals(3, table4.getMaxColumnsWithout(false, 0, 1,    3, 4, 5, 6, 7));
        assertEquals(4, table4.getMaxColumnsWithout(false, 0, 1, 2,    4, 5, 6, 7));
        assertEquals(1, table4.getMaxColumnsWithout(false, 0, 1, 2, 3,    5, 6, 7));
        assertEquals(2, table4.getMaxColumnsWithout(false, 0, 1, 2, 3, 4,    6, 7));
        assertEquals(3, table4.getMaxColumnsWithout(false, 0, 1, 2, 3, 4, 5,    7));
        assertEquals(4, table4.getMaxColumnsWithout(false, 0, 1, 2, 3, 4, 5, 6   ));
        // @formatter:on
    }

    @Test
    public void test_minColumnsWithout() {
        MarkdownTable table4 = getTable(markdown4);
        // @formatter:off
        assertEquals(0, table4.getMinColumnsWithout(true, 0, 1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(1, table4.getMinColumnsWithout(true,    1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(2, table4.getMinColumnsWithout(true, 0,    2, 3, 4, 5, 6, 7, 8));
        assertEquals(3, table4.getMinColumnsWithout(true, 0, 1,    3, 4, 5, 6, 7, 8));
        assertEquals(4, table4.getMinColumnsWithout(true, 0, 1, 2,    4, 5, 6, 7, 8));
        assertEquals(4, table4.getMinColumnsWithout(true, 0, 1, 2, 3,    5, 6, 7, 8));
        assertEquals(1, table4.getMinColumnsWithout(true, 0, 1, 2, 3, 4,    6, 7, 8));
        assertEquals(2, table4.getMinColumnsWithout(true, 0, 1, 2, 3, 4, 5,    7, 8));
        assertEquals(3, table4.getMinColumnsWithout(true, 0, 1, 2, 3, 4, 5, 6,    8));
        assertEquals(4, table4.getMinColumnsWithout(true, 0, 1, 2, 3, 4, 5, 6, 7   ));
        assertEquals(1, table4.getMinColumnsWithout(true));

        assertEquals(0, table4.getMinColumnsWithout(false, 0, 1, 2, 3, 4, 5, 6, 7));
        assertEquals(1, table4.getMinColumnsWithout(false,    1, 2, 3, 4, 5, 6, 7));
        assertEquals(2, table4.getMinColumnsWithout(false, 0,    2, 3, 4, 5, 6, 7));
        assertEquals(3, table4.getMinColumnsWithout(false, 0, 1,    3, 4, 5, 6, 7));
        assertEquals(4, table4.getMinColumnsWithout(false, 0, 1, 2,    4, 5, 6, 7));
        assertEquals(1, table4.getMinColumnsWithout(false, 0, 1, 2, 3,    5, 6, 7));
        assertEquals(2, table4.getMinColumnsWithout(false, 0, 1, 2, 3, 4,    6, 7));
        assertEquals(3, table4.getMinColumnsWithout(false, 0, 1, 2, 3, 4, 5,    7));
        assertEquals(4, table4.getMinColumnsWithout(false, 0, 1, 2, 3, 4, 5, 6   ));
        // @formatter:on
    }

    final static private String markdown6 = "" +
            "|  | Left-aligned1 |\n" +
            "|  | Left-aligned | Center-aligned2 |\n" +
            "|  | Left-aligned | Center-aligned | Right-aligned3 |\n" +
            "|  | Left-aligned | Center-aligned | Right-aligned | Right-aligned4 |\n" +
            "|  |  |  |  |  |\n" +
            "| --- | :---         |     :---:      |          ---: |          ---: |\n" +
            "|  | git status1  |\n" +
            "|  | git diff     | git diff2      |\n" +
            "|  | git diff     | git diff       | git diff3      |\n" +
            "|  | git diff     | git diff       | git diff       | git diff4      |\n" +
            "|  |  |  |  |  |\n" +
            "[Table Caption]\n" +
            "";

    @Test
    public void test_isEmptyColumn() {
        MarkdownTable table6 = getTable(markdown6);
        for (int i = 0; i < 10; i++) {
            assertEquals("Column: " + i, i == 0 || i > 4, table6.isEmptyColumn(i));
        }
    }

    @Test
    public void test_isEmptyRow() {
        MarkdownTable table5 = getTable(markdown5);
        for (int i = 0; i < 10; i++) {
            assertEquals("Row with sep: " + i, i == 4 || i == 10, table5.isEmptyRow(i, true));
            assertEquals("Row without sep: " + i, i == 4 || i == 9, table5.isEmptyRow(i, false));
        }
    }

    void assertIndexOf(int expIndex, int expSpanOffset, MarkdownTable.IndexSpanOffset index) {
        assertEquals(new MarkdownTable.IndexSpanOffset(expIndex, expSpanOffset).toString(), index.toString());
    }

    void assertIndexOf(String message, int expIndex, int expSpanOffset, MarkdownTable.IndexSpanOffset index) {
        assertEquals(message, new MarkdownTable.IndexSpanOffset(expIndex, expSpanOffset).toString(), index.toString());
    }

    final static private String markdown7 = "" +
            "| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |\n" +
            "| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |\n" +
            "|------------|------------|------------|------------|------------|\n" +
            "| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |\n" +
            "| Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |\n" +
            "| Data 3.1                           ||| Data 3.4   | Data 3.5   |\n" +
            "| Data 4.1                                       |||| Data 4.5   |\n" +
            "| Data 5.1                                                   |||||\n" +
            "|                                                            |||||\n" +
            "| Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |\n" +
            "| Data 7.1   | Data 7.2                           ||| Data 7.5   |\n" +
            "| Data 8.1   | Data 8.2                                       ||||\n" +
            "| Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |\n" +
            "| Data 10.1  | Data 10.2  | Data 10.3                          |||\n" +
            "| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||" +
            "";

    @Test
    public void test_indexOf() {
        MarkdownTable table7 = getTable(markdown7);

        // Data 1.1 row
        assertIndexOf(0, 0, table7.body.rows.get(0).indexOf(0));
        assertIndexOf(1, 0, table7.body.rows.get(0).indexOf(1));
        assertIndexOf(2, 0, table7.body.rows.get(0).indexOf(2));
        assertIndexOf(3, 0, table7.body.rows.get(0).indexOf(3));
        assertIndexOf(4, 0, table7.body.rows.get(0).indexOf(4));

        // Data 2.1 row
        assertIndexOf(0, 0, table7.body.rows.get(1).indexOf(0));
        assertIndexOf(0, 1, table7.body.rows.get(1).indexOf(1));
        assertIndexOf(1, 0, table7.body.rows.get(1).indexOf(2));
        assertIndexOf(2, 0, table7.body.rows.get(1).indexOf(3));
        assertIndexOf(3, 0, table7.body.rows.get(1).indexOf(4));

        // Data 3.1 row
        assertIndexOf(0, 0, table7.body.rows.get(2).indexOf(0));
        assertIndexOf(0, 1, table7.body.rows.get(2).indexOf(1));
        assertIndexOf(0, 2, table7.body.rows.get(2).indexOf(2));
        assertIndexOf(1, 0, table7.body.rows.get(2).indexOf(3));
        assertIndexOf(2, 0, table7.body.rows.get(2).indexOf(4));

        // Data 4.1 row
        assertIndexOf(0, 0, table7.body.rows.get(3).indexOf(0));
        assertIndexOf(0, 1, table7.body.rows.get(3).indexOf(1));
        assertIndexOf(0, 2, table7.body.rows.get(3).indexOf(2));
        assertIndexOf(0, 3, table7.body.rows.get(3).indexOf(3));
        assertIndexOf(1, 0, table7.body.rows.get(3).indexOf(4));

        // Data 5.1 row
        assertIndexOf(0, 0, table7.body.rows.get(4).indexOf(0));
        assertIndexOf(0, 1, table7.body.rows.get(4).indexOf(1));
        assertIndexOf(0, 2, table7.body.rows.get(4).indexOf(2));
        assertIndexOf(0, 3, table7.body.rows.get(4).indexOf(3));
        assertIndexOf(0, 4, table7.body.rows.get(4).indexOf(4));

        // Empty Data row with span 5
        assertIndexOf(0, 0, table7.body.rows.get(5).indexOf(0));
        assertIndexOf(0, 1, table7.body.rows.get(5).indexOf(1));
        assertIndexOf(0, 2, table7.body.rows.get(5).indexOf(2));
        assertIndexOf(0, 3, table7.body.rows.get(5).indexOf(3));
        assertIndexOf(0, 4, table7.body.rows.get(5).indexOf(4));
    }

    // these are tested with manipulators
    @Test
    public void allRows() {
    }

    @Test
    public void allRowCount() {
    }

    @Test
    public void forAllRows() {
    }

    @Test
    public void deleteRows() {
    }

    @Test
    public void insertColumns() {
    }

    @Test
    public void deleteColumns() {
    }

    @Test
    public void insertRows() {
    }

    @Test
    public void moveColumn() {
    }
}

