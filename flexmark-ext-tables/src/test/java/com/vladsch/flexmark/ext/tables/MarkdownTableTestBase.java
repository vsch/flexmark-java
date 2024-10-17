package com.vladsch.flexmark.ext.tables;

import static com.vladsch.flexmark.util.format.TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR;
import static org.junit.Assert.assertEquals;

import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableCellOffsetInfo;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MarkdownTableTestBase {
  MarkdownTable[] getTables(CharSequence markdown) {
    return getTables(markdown, null);
  }

  private MarkdownTable[] getTables(CharSequence markdown, DataHolder options) {
    if (options == null) {
      options =
          new MutableDataSet()
              .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()))
              .toImmutable();
    } else {
      options =
          new MutableDataSet(options)
              .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()))
              .toImmutable();
    }

    Parser parser = Parser.builder(options).build();
    Node document = parser.parse(BasedSequence.of(markdown));
    TableExtractingVisitor tableVisitor = new TableExtractingVisitor(options);
    return tableVisitor.getTables(document);
  }

  String getFormattedTable(MarkdownTable table) {
    MarkdownWriter out = new MarkdownWriter(LineAppendable.F_FORMAT_ALL);
    table.appendTable(out);
    return out.toString(0, 0);
  }

  String[] getFormattedTables(MarkdownTable[] tables) {
    List<String> formatted = new ArrayList<>();

    for (MarkdownTable table : tables) {
      MarkdownWriter out = new MarkdownWriter(LineAppendable.F_FORMAT_ALL);
      table.appendTable(out);
      formatted.add(out.toString(0, 0));
    }
    return formatted.toArray(new String[0]);
  }

  MarkdownTable getTable(CharSequence markdown) {
    return getTable(markdown, null);
  }

  MarkdownTable getTable(CharSequence markdown, DataHolder options) {
    MarkdownTable table = getTables(markdown, options)[0];
    table.normalize();
    return table;
  }

  DataHolder formatOptions(DataHolder options) {
    return (options == null ? new MutableDataSet() : new MutableDataSet(options))
        .set(TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX, "")
        .set(TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
        .set(TableFormatOptions.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
        .set(TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
        .set(TableFormatOptions.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
        .set(TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.ADD)
        .set(TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.ADD)
        .set(TableFormatOptions.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
        .set(TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
        .set(TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
        .set(TableFormatOptions.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
        .set(TableFormatOptions.FORMAT_TABLE_TRIM_CELL_WHITESPACE, true)
        .set(
            TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER,
            new CharWidthProvider() {
              @Override
              public int getSpaceWidth() {
                return 1;
              }

              @Override
              public int getCharWidth(char c) {
                return c == INTELLIJ_DUMMY_IDENTIFIER_CHAR ? 0 : 1;
              }
            });
  }

  DataHolder formatOptionsAsIs(DataHolder options) {
    return (options == null ? new MutableDataSet() : new MutableDataSet(options))
        .set(TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX, "")
        .set(TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH, 3)
        .set(TableFormatOptions.FORMAT_TABLE_LEAD_TRAIL_PIPES, true)
        .set(TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH, true)
        .set(TableFormatOptions.FORMAT_TABLE_FILL_MISSING_COLUMNS, true)
        .set(TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.AS_IS)
        .set(TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.AS_IS)
        .set(TableFormatOptions.FORMAT_TABLE_SPACE_AROUND_PIPES, true)
        .set(TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT, true)
        .set(TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_DASHES, 3)
        .set(TableFormatOptions.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
        .set(TableFormatOptions.FORMAT_TABLE_TRIM_CELL_WHITESPACE, true)
        .set(
            TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER,
            new CharWidthProvider() {
              @Override
              public int getSpaceWidth() {
                return 1;
              }

              @Override
              public int getCharWidth(char c) {
                return c == INTELLIJ_DUMMY_IDENTIFIER_CHAR ? 0 : 1;
              }
            });
  }

  void assertIndexOf(int expIndex, int expSpanOffset, MarkdownTable.IndexSpanOffset index) {
    assertEquals(
        new MarkdownTable.IndexSpanOffset(expIndex, expSpanOffset).toString(), index.toString());
  }

  void assertCellInfo(
      String message,
      int row,
      int column,
      Integer insideCol,
      Integer insideOffset,
      TableCellOffsetInfo info) {
    assertEquals(
        message,
        new TableCellOffsetInfo(
                info.offset,
                info.table,
                info.section,
                null,
                null,
                row,
                column,
                insideCol,
                insideOffset)
            .toString(),
        info.toString());
  }
}
