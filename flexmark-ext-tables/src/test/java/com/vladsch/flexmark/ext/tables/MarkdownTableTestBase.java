package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.ColumnSort;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableCellOffsetInfo;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vladsch.flexmark.util.format.TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR;
import static org.junit.Assert.assertEquals;

public class MarkdownTableTestBase {
    protected MarkdownTable[] getTables(CharSequence markdown) {
        return getTables(markdown, null);
    }

    protected MarkdownTable[] getTables(CharSequence markdown, DataHolder options) {
        if (options == null) {
            options = new MutableDataSet()
                    .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()))
                    .toImmutable();
        } else {
            options = new MutableDataSet(options)
                    .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()))
                    .toImmutable();
        }

        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(BasedSequence.of(markdown));
        TableExtractingVisitor tableVisitor = new TableExtractingVisitor(options);
        return tableVisitor.getTables(document);
    }

    protected String getFormattedTable(MarkdownTable table) {
        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        table.appendTable(out);
        return out.toString(0, 0);
    }

    protected String[] getFormattedTables(MarkdownTable[] tables) {
        List<String> formatted = new ArrayList<>();

        for (MarkdownTable table : tables) {
            MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
            table.appendTable(out);
            formatted.add(out.toString(0, 0));
        }
        return formatted.toArray(new String[0]);
    }

    protected MarkdownTable getTable(CharSequence markdown) {
        return getTable(markdown, null);
    }

    protected String getTransposedTable(MarkdownTable table) {
        return getTransposedTable(table, 1);
    }

    protected String getTransposedTable(MarkdownTable table, int columnHeaders) {
        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable transposed = table.transposed(columnHeaders);
        transposed.appendTable(out);
        return out.toString(0, 0);
    }

    protected MarkdownTable getSortedTable(MarkdownTable table, ColumnSort[] columnSorts) {
        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        return table.sorted(columnSorts, 0, null);
    }

    protected MarkdownTable getTable(CharSequence markdown, DataHolder options) {
        MarkdownTable table = getTables(markdown, options)[0];
        table.normalize();
        return table;
    }

    DataHolder formatOptions(CharSequence tableIndentPrefix, DataHolder options) {
        MutableDataSet useOptions = (options == null ? new MutableDataSet() : new MutableDataSet(options))
                //.set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true)
                .set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, "")
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
                .set(TablesExtension.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
                .set(TablesExtension.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
                .set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.ADD)
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.ADD)
                .set(TablesExtension.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
                .set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_TRIM_CELL_WHITESPACE, true)
                .set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() {
                    @Override
                    public int getSpaceWidth() {
                        return 1;
                    }

                    @Override
                    public int getCharWidth(char c) {
                        return c == INTELLIJ_DUMMY_IDENTIFIER_CHAR ? 0 : 1;
                    }
                });
        return useOptions;
    }

    DataHolder formatOptionsAsIs(CharSequence tableIndentPrefix, DataHolder options) {
        MutableDataSet useOptions = (options == null ? new MutableDataSet() : new MutableDataSet(options))
                //.set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true)
                .set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, "")
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
                .set(TablesExtension.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
                .set(TablesExtension.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
                .set(TablesExtension.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
                .set(TablesExtension.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
                .set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
                .set(TablesExtension.FORMAT_TABLE_TRIM_CELL_WHITESPACE, true)
                .set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() {
                    @Override
                    public int getSpaceWidth() {
                        return 1;
                    }

                    @Override
                    public int getCharWidth(char c) {
                        return c == INTELLIJ_DUMMY_IDENTIFIER_CHAR ? 0 : 1;
                    }
                });
        return useOptions;
    }

    protected void assertIndexOf(int expIndex, int expSpanOffset, MarkdownTable.IndexSpanOffset index) {
        assertEquals(new MarkdownTable.IndexSpanOffset(expIndex, expSpanOffset).toString(), index.toString());
    }

    protected void assertIndexOf(
            String message,
            int expIndex,
            int expSpanOffset,
            MarkdownTable.IndexSpanOffset index
    ) {
        assertEquals(message, new MarkdownTable.IndexSpanOffset(expIndex, expSpanOffset).toString(), index.toString());
    }

    protected void assertCellInfo(
            String message,
            int row,
            int column,
            Integer insideCol,
            Integer insideOffset,
            TableCellOffsetInfo info
    ) {
        assertEquals(message, new TableCellOffsetInfo(info.offset, info.table, info.section, null, null, row, column, insideCol, insideOffset).toString(), info.toString());
    }
}

