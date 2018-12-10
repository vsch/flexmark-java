package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.formatter.internal.MarkdownWriter;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableCellOffsetInfo;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.vladsch.flexmark.util.format.TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarkdownTableTest {

    private MarkdownTable[] getTables(CharSequence markdown) {
        return getTables(markdown, null);
    }

    private MarkdownTable[] getTables(CharSequence markdown, DataHolder options) {
        if (options == null) {
            options = new MutableDataSet()
                    .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
        } else {
            options = new MutableDataSet(options)
                    .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
        }

        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(BasedSequenceImpl.of(markdown));
        TableExtractingVisitor tableVisitor = new TableExtractingVisitor(options);
        return tableVisitor.getTables(document);
    }

    private String getFormattedTable(MarkdownTable table) {
        return getFormattedTable(table, "", "");
    }

    private String getFormattedTable(MarkdownTable table, String linePrefix, String intellijDummyIdentifier) {
        MarkdownWriter out = new MarkdownWriter(new StringBuilder(), MarkdownWriter.FORMAT_ALL);
        table.appendTable(out);
        out.flush();
        return out.toString();
    }

    private String[] getFormattedTables(MarkdownTable[] tables) {
        List<String> formatted = new ArrayList<>();

        for (MarkdownTable table : tables) {
            MarkdownWriter out = new MarkdownWriter(new StringBuilder(), MarkdownWriter.FORMAT_ALL);
            table.appendTable(out);
            out.flush();
            formatted.add(out.toString());
        }
        return formatted.toArray(new String[0]);
    }

    private MarkdownTable getTable(CharSequence markdown) {
        return getTable(markdown, null);
    }

    private MarkdownTable getTable(CharSequence markdown, DataHolder options) {
        MarkdownTable table = getTables(markdown, options)[0];
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

    final static private String markdown8 = "some text\n\n" +
            "| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |\n" +
            "| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |\n" +
            "|------------|------------|------------|------------|------------|\n" +
            "| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |\n" +
            "| Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |\n" +
            "| Data 3.1                           ||| Data 3.4   | Data 3.5   |\n" +
            "| Data 4.1                                       |||| Data 4.5   |\n" +
            "";

    void assertCellInfo(String message, int row, int column, Integer insideCol, Integer insideOffset, TableCellOffsetInfo info) {
        assertEquals(message, new TableCellOffsetInfo(info.offset, info.table, info.section, null, null, row, column, insideCol, insideOffset).toString(), info.toString());
    }

    @Test
    public void test_ExactColumn() {
        MarkdownTable table8 = getTable(markdown8);
        int offset = 11;

        assertEquals(offset, table8.getTableStartOffset());

        assertCellInfo("", 0, 0, null, null, table8.getCellOffsetInfo(10));
        assertCellInfo("", 0, 0, null, null, table8.getCellOffsetInfo(11));
        assertCellInfo("", 0, 0, 0, 0, table8.getCellOffsetInfo(offset + 1));
        assertCellInfo("", 0, 0, 0, 1, table8.getCellOffsetInfo(offset + 2));

        // last cell
        assertCellInfo("", 0, 4, 4, 10, table8.getCellOffsetInfo(offset + 64));
        assertCellInfo("", 0, 4, 4, 11, table8.getCellOffsetInfo(offset + 65));
        assertCellInfo("", 0, 5, null, null, table8.getCellOffsetInfo(offset + 66));

        // line 2
        assertCellInfo("", 1, 0, null, null, table8.getCellOffsetInfo(offset + 67));
        assertCellInfo("", 1, 0, 0, 0, table8.getCellOffsetInfo(offset + 68));
    }

    DataHolder formatOptions(CharSequence tableIndentPrefix, DataHolder options) {
        MutableDataSet useOptions = (options == null ? new MutableDataSet() : new MutableDataSet(options))
                .set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true)
                .set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, "")
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
                .set(TablesExtension.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
                .set(TablesExtension.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
                .set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.ADD)
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
                .set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_TRIM_CELL_WHITESPACE, true)
                .set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() {
                    @Override
                    public int spaceWidth() {
                        return 1;
                    }

                    @Override
                    public int charWidth(final char c) {
                        return c == INTELLIJ_DUMMY_IDENTIFIER_CHAR ? 0 : 1;
                    }

                    @Override
                    public int charWidth(final CharSequence s) {
                        return BasedSequenceImpl.of(s).countNotChars(INTELLIJ_DUMMY_IDENTIFIER_CHAR);
                    }
                })

                .set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() {
                    public int spaceWidth() {
                        return 1;
                    }

                    public int charWidth(char c) {
                        return (c == INTELLIJ_DUMMY_IDENTIFIER_CHAR) ? 0 : 1;
                    }

                    public int charWidth(CharSequence s) {
                        if (s == null) return 0;
                        return s.length() - BasedSequenceImpl.of(s).countChars(INTELLIJ_DUMMY_IDENTIFIER_CHAR);
                    }
                });
        return useOptions;
    }

    @Test
    public void test_trackingOffset() {
        String markdown = "" +
                "| Header 1.1 | Header 1.2 |\n" +
                "| Header 2.1 | Header 2.2 |\n" +
                "|------------|------------|\n" +
                "| Data 1.1 ^ Long | Data 1.2   |\n" +
                "| Data 2.1               ||\n" +
                "| Data 3.1                |\n" +
                "| Data 4.1                |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Header 1.1     | Header 1.2 |\n" +
                "| Header 2.1     | Header 2.2 |\n" +
                "|:---------------|:-----------|\n" +
                "| Data 1.1  Long | Data 1.2   |\n" +
                "| Data 2.1                   ||\n" +
                "| Data 3.1       |            |\n" +
                "| Data 4.1       |            |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Header 1.1     | Header 1.2 |\n" +
                "| Header 2.1     | Header 2.2 |\n" +
                "|:---------------|:-----------|\n" +
                "| Data 1.1 ^ Long | Data 1.2   |\n" +
                "| Data 2.1                   ||\n" +
                "| Data 3.1       |            |\n" +
                "| Data 4.1       |            |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 12, offset);
    }

    @Test
    public void test_trackingOffset2() {
        String markdown = "" +
                "| Header 1.1 | Header 1.2 |\n" +
                "| Header 2.1 | Header 2.2 |\n" +
                "|------------|------------|\n" +
                "| Data 2.1               ||\n" +
                "| Data 3.1                |\n" +
                "| Data 1.1 ^ Long | Data 1.2   |\n" +
                "| Data 4.1                |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Header 1.1     | Header 1.2 |\n" +
                "| Header 2.1     | Header 2.2 |\n" +
                "|:---------------|:-----------|\n" +
                "| Data 2.1                   ||\n" +
                "| Data 3.1       |            |\n" +
                "| Data 1.1  Long | Data 1.2   |\n" +
                "| Data 4.1       |            |\n" +
                "" +
                "", formattedTable);
        assertEquals(pos + 20, offset);
        assertEquals("" +
                "| Header 1.1     | Header 1.2 |\n" +
                "| Header 2.1     | Header 2.2 |\n" +
                "|:---------------|:-----------|\n" +
                "| Data 2.1                   ||\n" +
                "| Data 3.1       |            |\n" +
                "| Data 1.1 ^ Long | Data 1.2   |\n" +
                "| Data 4.1       |            |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
    }

    @Test
    public void test_trackingOffset3() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see whats^ the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("| Features                                                                         | Basic | Enhanced |    |\n" +
                "|:---------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                 |   X   |    X     |    |\n" +
                "| Preview Tab so you can see whats the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                              |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("| Features                                                                         | Basic | Enhanced |    |\n" +
                "|:---------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                 |   X   |    X     |    |\n" +
                "| Preview Tab so you can see whats^ the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                              |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 3, offset);
    }

    @Test
    public void test_trackingOffset4() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------:^|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|:-------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|:-------------------------------------------------------------------------------:^|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_LeftAligned_TypedSpaceAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. ^ |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. ^|   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_LeftAligned_BackspaceSpaceAfter() {
        String markdown = "" +
                "| Features                                                                           | Basic | Enhanced |    |\n" +
                "|:-----------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                   |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub.    ^|   X   |    X     |    |\n" +
                "| Syntax highlighting                                                                |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                           | Basic | Enhanced |    |\n" +
                "|:-----------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                   |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub.    |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                                |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                           | Basic | Enhanced |    |\n" +
                "|:-----------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                   |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub.    ^|   X   |    X     |    |\n" +
                "| Syntax highlighting                                                                |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_LeftAligned_TypedSpaceAfterGrow() {
        String markdown = "" +
                "|                                     Features                                     | Basic | Enhanced |\n" +
                "|----------------------------------------------------------------------------------|:-----:|---------:|\n" +
                "| Works with builds 143.2730 or newer, product IDEA 14.1.4                         |   X   |        X |\n" +
                "| Preview Tab so you can see whats the rendered markdown will look like on GitHub. ^ |   X   |        X |\n" +
                "| Syntax highlighting                                                              |   X   |        X |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                         | Basic | Enhanced |\n" +
                "|:---------------------------------------------------------------------------------|:-----:|---------:|\n" +
                "| Works with builds 143.2730 or newer, product IDEA 14.1.4                         |   X   |        X |\n" +
                "| Preview Tab so you can see whats the rendered markdown will look like on GitHub. |   X   |        X |\n" +
                "| Syntax highlighting                                                              |   X   |        X |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                         | Basic | Enhanced |\n" +
                "|:---------------------------------------------------------------------------------|:-----:|---------:|\n" +
                "| Works with builds 143.2730 or newer, product IDEA 14.1.4                         |   X   |        X |\n" +
                "| Preview Tab so you can see whats the rendered markdown will look like on GitHub. ^|   X   |        X |\n" +
                "| Syntax highlighting                                                              |   X   |        X |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_LeftAligned_TypedSpaceAfterFixed() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4 ^                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4 ^               |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_RightAligned_TypedSpaceAfterGrow() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                | XXXXXXXXX ^ |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        |     Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|----------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                | XXXXXXXXX |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |         X |    X     |    |\n" +
                "| Syntax highlighting                                                             |         X |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        |     Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|----------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                | XXXXXXXXX ^|    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |         X |    X     |    |\n" +
                "| Syntax highlighting                                                             |         X |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 8, offset);
    }

    @Test
    public void test_LeftAligned_BackSpacesAfterFixed() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4   ^           |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4   ^             |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_LeftAligned_TypedSpaceBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "|  ^Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| ^Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_LeftAligned_2SpacesBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| ^      Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| ^Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_Centered_TypedSpaceAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X ^  |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                        "| Syntax highlighting                                                             |   X   |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X ^  |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_Centered_Typed2SpacesAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X  ^  |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |  X    |    X     |    |\n" +
                        "| Syntax highlighting                                                             |   X   |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |  X  ^  |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_Centered_Typed3SpacesAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |  X    |    X     |    |\n" +
                        "| Syntax highlighting                                                             |   X   |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |  X   ^ |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_Centered_TypedExtraSpacesAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. | X     ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. | X     |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. | X     ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_Centered_TypedExtra2SpacesAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. | X      ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic  | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X    |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. | X      |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X    |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic  | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X    |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. | X      ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |   X    |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 3, offset);
    }

    @Test
    public void test_Centered_TypedSpaceBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |    ^X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                        "| Syntax highlighting                                                             |   X   |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   ^X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_RightAligned_TypedSpaceAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     X ^ |    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     X |    X     |    |\n" +
                        "| Syntax highlighting                                                             |     X |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     X ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_RightAligned_Typed2SpacesAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     X  ^ |    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |    X  |    X     |    |\n" +
                        "| Syntax highlighting                                                             |     X |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |    X  ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_RightAligned_Typed3SpacesAfter() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     X   ^ |    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos, true));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "| Features                                                                        | Basic | Enhanced |    |\n" +
                        "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                        "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                        "| Syntax highlighting                                                             |     X |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   ^|    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 2, offset);
    }

    @Test
    public void test_RightAligned_TypedSpaceBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |      ^X |    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     X |    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|------:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |     X |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |     ^X |    X     |    |\n" +
                "| Syntax highlighting                                                             |     X |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 1, offset);
    }

    @Test
    public void test_Separator_LeftAlignedBackspaceFirstColon() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 1, offset);
    }

    @Test
    public void test_Separator_TypedDashAfter2() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|-^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:-^-------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 1, offset);
    }

    @Test
    public void test_Separator_TypedColonBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_Separator_RightAlignedTypedColonBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:^-------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                        "|                                    Features                                     | Basic | Enhanced |    |\n" +
                        "|:-------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                        "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                        "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                        "|                               Syntax highlighting                               |   X   |    X     |    |\n",
                formattedTable);
        assertEquals("" +
                "|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|:^-------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_Separator_LeftAlignedBackspaceColonBefore() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:^--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 1, offset);
    }

    @Test
    public void test_Separator_CenteredBackspaceFirstColon() {
        String markdown = "" +
                "|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|^------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "|                                                                        Features | Basic | Enhanced |    |\n" +
                "|--------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                "|                Works with builds 143.2730 or newer, product version IDEA 14.1.4 |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                                                             Syntax highlighting |   X   |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "|                                                                        Features | Basic | Enhanced |    |\n" +
                "|^--------------------------------------------------------------------------------:|:-----:|:--------:|:---|\n" +
                "|                Works with builds 143.2730 or newer, product version IDEA 14.1.4 |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                                                             Syntax highlighting |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos, offset);
    }

    @Test
    public void test_Separator_CenteredBackspaceLastColon() {
        String markdown = "" +
                "|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|:------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 2, offset);
    }

    @Test
    public void test_Separator_CenteredBackspaceLastColonRemoveLeftColon() {
        String markdown = "" +
                "|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|:------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null).toMutable().set(TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.REMOVE));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "|                                    Features                                     | Basic | Enhanced |   |\n" +
                "|---------------------------------------------------------------------------------|:-----:|:--------:|---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |   |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |   |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |   |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "|                                    Features                                     | Basic | Enhanced |   |\n" +
                "|---------------------------------------------------------------------------------^|:-----:|:--------:|---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |   |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |   |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |   |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos + 1, offset);
    }

    @Test
    public void test_Separator_CenteredBackspaceLastColon2() {
        String markdown = "" +
                "|                                    Features                                     | Basic | Enhanced |    |\n" +
                "|:------------------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "|        Works with builds 143.2730 or newer, product version IDEA 14.1.4         |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "|                               Syntax highlighting                               |   X   |    X     |    |\n" +
                "";

        int pos = markdown.indexOf("^");
        BasedSequence source = BasedSequenceImpl.of(markdown.substring(0, pos) + markdown.substring(pos + 1));
        MarkdownTable table = getTable(source, formatOptions("", null));
        assertTrue(table.addTrackedOffset(pos));
        HtmlWriter out = new HtmlWriter(new StringBuilder(), 0, HtmlWriter.FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.getText();
        Map<Integer, Integer> offsets = table.getTrackedOffsets();
        int offset = offsets.get(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "" +
                "", formattedTable);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "", formattedTable.substring(0, offset) + "^" + formattedTable.substring(offset));
        assertEquals(pos - 10, offset);
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

