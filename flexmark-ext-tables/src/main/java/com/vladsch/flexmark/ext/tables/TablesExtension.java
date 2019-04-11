package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ext.tables.internal.TableJiraRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableNodeFormatter;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableParagraphPreProcessor;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.TableManipulator;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for GFM tables using "|" pipes (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed tables are turned into {@link TableBlock} blocks.
 */
@SuppressWarnings("WeakerAccess")
public class TablesExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    public static final DataKey<Boolean> TRIM_CELL_WHITESPACE = new DataKey<>("TRIM_CELL_WHITESPACE", true);
    public static final DataKey<Integer> MIN_SEPARATOR_DASHES = new DataKey<>("MIN_SEPARATOR_DASHES", 3);
    public static final DataKey<Integer> MAX_HEADER_ROWS = new DataKey<>("MAX_HEADER_ROWS", Integer.MAX_VALUE);
    public static final DataKey<Integer> MIN_HEADER_ROWS = new DataKey<>("MIN_HEADER_ROWS", 0);
    public static final DataKey<Boolean> APPEND_MISSING_COLUMNS = new DataKey<>("APPEND_MISSING_COLUMNS", false);
    public static final DataKey<Boolean> DISCARD_EXTRA_COLUMNS = new DataKey<>("DISCARD_EXTRA_COLUMNS", false);
    public static final DataKey<Boolean> COLUMN_SPANS = new DataKey<>("COLUMN_SPANS", true);
    public static final DataKey<Boolean> HEADER_SEPARATOR_COLUMN_MATCH = new DataKey<>("HEADER_SEPARATOR_COLUMN_MATCH", false);
    public static final DataKey<String> CLASS_NAME = new DataKey<>("CLASS_NAME", "");
    public static final DataKey<Boolean> WITH_CAPTION = new DataKey<>("WITH_CAPTION", true);
    public static final DataKey<Boolean> MULTI_LINE_ROWS = new DataKey<>("MULTI_LINE_ROWS", false);

    // format options copy from TableFormatOptions
    public static final DataKey<Boolean> FORMAT_TABLE_TRIM_CELL_WHITESPACE = TableFormatOptions.FORMAT_TABLE_TRIM_CELL_WHITESPACE;
    public static final DataKey<Boolean> FORMAT_TABLE_LEAD_TRAIL_PIPES = TableFormatOptions.FORMAT_TABLE_LEAD_TRAIL_PIPES;
    public static final DataKey<Boolean> FORMAT_TABLE_SPACE_AROUND_PIPES = TableFormatOptions.FORMAT_TABLE_SPACE_AROUND_PIPES;
    public static final DataKey<Boolean> FORMAT_TABLE_ADJUST_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH;
    public static final DataKey<Boolean> FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT = TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT;
    public static final DataKey<Boolean> FORMAT_TABLE_FILL_MISSING_COLUMNS = TableFormatOptions.FORMAT_TABLE_FILL_MISSING_COLUMNS;
    public static final DataKey<DiscretionaryText> FORMAT_TABLE_LEFT_ALIGN_MARKER = TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER;
    public static final DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH;
    public static final DataKey<Integer> FORMAT_TABLE_MIN_SEPARATOR_DASHES = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_DASHES;
    public static final DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER;
    public static final DataKey<TableManipulator> FORMAT_TABLE_MANIPULATOR = TableFormatOptions.FORMAT_TABLE_MANIPULATOR;
    public static final DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = TableFormatOptions.FORMAT_TABLE_CAPTION;
    public static final DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES;
    public static final DataKey<String> FORMAT_TABLE_INDENT_PREFIX = TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX;

    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FORMAT_LEAD_TRAIL_PIPES = TableFormatOptions.FORMAT_TABLE_LEAD_TRAIL_PIPES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FORMAT_SPACE_AROUND_PIPES = TableFormatOptions.FORMAT_TABLE_SPACE_AROUND_PIPES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FORMAT_ADJUST_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_ADJUST_COLUMN_WIDTH;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FORMAT_APPLY_COLUMN_ALIGNMENT = TableFormatOptions.FORMAT_TABLE_APPLY_COLUMN_ALIGNMENT;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FORMAT_FILL_MISSING_COLUMNS = TableFormatOptions.FORMAT_TABLE_FILL_MISSING_COLUMNS;

    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FORMAT_REMOVE_CAPTION = TableFormatOptions.REMOVE_CAPTION;

    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<DiscretionaryText> FORMAT_LEFT_ALIGN_MARKER = TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Integer> FORMAT_MIN_SEPARATOR_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Integer> FORMAT_MIN_SEPARATOR_DASHES = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_DASHES;

    /**
     * @deprecated use FORMAT_TABLE_CAPTION with enum value, this option only has effect FORMAT_TABLE_CAPTION is set to AS_IS
     */
    @Deprecated public static final DataKey<Boolean> REMOVE_CAPTION = TableFormatOptions.REMOVE_CAPTION;

    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> LEAD_TRAIL_PIPES = TableFormatOptions.LEAD_TRAIL_PIPES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> SPACE_AROUND_PIPES = TableFormatOptions.SPACE_AROUND_PIPES;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> ADJUST_COLUMN_WIDTH = TableFormatOptions.ADJUST_COLUMN_WIDTH;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> APPLY_COLUMN_ALIGNMENT = TableFormatOptions.APPLY_COLUMN_ALIGNMENT;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Boolean> FILL_MISSING_COLUMNS = TableFormatOptions.FILL_MISSING_COLUMNS;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<DiscretionaryText> LEFT_ALIGN_MARKER = TableFormatOptions.LEFT_ALIGN_MARKER;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<Integer> MIN_SEPARATOR_COLUMN_WIDTH = TableFormatOptions.MIN_SEPARATOR_COLUMN_WIDTH;
    /**
     * @deprecated use FORMAT_TABLE_ prefixed name
     */
    @Deprecated public static final DataKey<CharWidthProvider> CHAR_WIDTH_PROVIDER = TableFormatOptions.CHAR_WIDTH_PROVIDER;

    public static Extension create() {
        return new TablesExtension();
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new TableNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.paragraphPreProcessorFactory(TableParagraphPreProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new TableNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new TableJiraRenderer.Factory());
        }
    }
}
