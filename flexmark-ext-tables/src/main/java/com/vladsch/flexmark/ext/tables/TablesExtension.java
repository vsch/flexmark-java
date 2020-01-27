package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ext.tables.internal.TableJiraRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableNodeFormatter;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableParagraphPreProcessor;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.TableManipulator;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for GFM tables using "|" pipes (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed tables are turned into {@link TableBlock} blocks.
 */
@SuppressWarnings("WeakerAccess")
public class TablesExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    final public static DataKey<Boolean> TRIM_CELL_WHITESPACE = new DataKey<>("TRIM_CELL_WHITESPACE", true);
    final public static DataKey<Integer> MIN_SEPARATOR_DASHES = new DataKey<>("MIN_SEPARATOR_DASHES", 3);
    final public static DataKey<Integer> MAX_HEADER_ROWS = new DataKey<>("MAX_HEADER_ROWS", Integer.MAX_VALUE);
    final public static DataKey<Integer> MIN_HEADER_ROWS = new DataKey<>("MIN_HEADER_ROWS", 0);
    final public static DataKey<Boolean> APPEND_MISSING_COLUMNS = new DataKey<>("APPEND_MISSING_COLUMNS", false);
    final public static DataKey<Boolean> DISCARD_EXTRA_COLUMNS = new DataKey<>("DISCARD_EXTRA_COLUMNS", false);
    final public static DataKey<Boolean> COLUMN_SPANS = new DataKey<>("COLUMN_SPANS", true);
    final public static DataKey<Boolean> HEADER_SEPARATOR_COLUMN_MATCH = new DataKey<>("HEADER_SEPARATOR_COLUMN_MATCH", false);
    final public static DataKey<String> CLASS_NAME = new DataKey<>("CLASS_NAME", "");
    final public static DataKey<Boolean> WITH_CAPTION = new DataKey<>("WITH_CAPTION", true);

    // format options copy from TableFormatOptions
    final public static DataKey<Boolean> FORMAT_TABLE_TRIM_CELL_WHITESPACE = TableFormatOptions.FORMAT_TABLE_TRIM_CELL_WHITESPACE;
    final public static DataKey<Boolean> FORMAT_TABLE_LEAD_TRAIL_PIPES = TableFormatOptions.FORMAT_TABLE_LEAD_TRAIL_PIPES;
    final public static DataKey<Boolean> FORMAT_TABLE_SPACE_AROUND_PIPES = TableFormatOptions.FORMAT_TABLE_SPACE_AROUND_PIPES;
    final public static DataKey<Boolean> FORMAT_TABLE_ADJUST_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH;
    final public static DataKey<Boolean> FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT = TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT;
    final public static DataKey<Boolean> FORMAT_TABLE_FILL_MISSING_COLUMNS = TableFormatOptions.FORMAT_TABLE_FILL_MISSING_COLUMNS;

    // QUERY: is this still needed???
    final public static NullableDataKey<Integer> FORMAT_TABLE_FILL_MISSING_MIN_COLUMN = TableFormatOptions.FORMAT_TABLE_FILL_MISSING_MIN_COLUMN;

    final public static DataKey<DiscretionaryText> FORMAT_TABLE_LEFT_ALIGN_MARKER = TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER;
    final public static DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH;
    final public static DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_DASHES = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_DASHES;
    final public static DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER;
    final public static DataKey<TableManipulator> FORMAT_TABLE_MANIPULATOR = TableFormatOptions.FORMAT_TABLE_MANIPULATOR;
    final public static DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = TableFormatOptions.FORMAT_TABLE_CAPTION;
    final public static DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES;
    final public static DataKey<String> FORMAT_TABLE_INDENT_PREFIX = TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX;

    public static TablesExtension create() {
        return new TablesExtension();
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new TableNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.paragraphPreProcessorFactory(TableParagraphPreProcessor.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new TableNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new TableJiraRenderer.Factory());
        }
    }
}
