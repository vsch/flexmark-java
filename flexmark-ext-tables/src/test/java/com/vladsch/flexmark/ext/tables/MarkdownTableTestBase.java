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

public class MarkdownTableTestBase {
    protected MarkdownTable[] getTables(CharSequence markdown) {
        return getTables(markdown, null);
    }

    protected MarkdownTable[] getTables(CharSequence markdown, DataHolder options) {
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

    protected String getFormattedTable(MarkdownTable table) {
        return getFormattedTable(table, "", "");
    }

    protected String getFormattedTable(
            MarkdownTable table,
            String linePrefix,
            String intellijDummyIdentifier
    ) {
        MarkdownWriter out = new MarkdownWriter(new StringBuilder(), MarkdownWriter.FORMAT_ALL);
        table.appendTable(out);
        out.flush();
        return out.toString();
    }

    protected String[] getFormattedTables(MarkdownTable[] tables) {
        List<String> formatted = new ArrayList<>();

        for (MarkdownTable table : tables) {
            MarkdownWriter out = new MarkdownWriter(new StringBuilder(), MarkdownWriter.FORMAT_ALL);
            table.appendTable(out);
            out.flush();
            formatted.add(out.toString());
        }
        return formatted.toArray(new String[0]);
    }

    protected MarkdownTable getTable(CharSequence markdown) {
        return getTable(markdown, null);
    }

    protected MarkdownTable getTable(CharSequence markdown, DataHolder options) {
        MarkdownTable table = getTables(markdown, options)[0];
        table.normalize();
        return table;
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
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.ADD)
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

