package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.tables.internal.TableJiraRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableNodeFormatter;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableParagraphPreProcessor;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for GFM tables using "|" pipes (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed tables are turned into {@link TableBlock} blocks.
 * </p>
 */
@SuppressWarnings("WeakerAccess")
public class TablesExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    public static final DataKey<Integer> MAX_HEADER_ROWS = new DataKey<>("MAX_HEADER_ROWS", Integer.MAX_VALUE);
    public static final DataKey<Integer> MIN_HEADER_ROWS = new DataKey<>("MIN_HEADER_ROWS", 0);
    public static final DataKey<Boolean> APPEND_MISSING_COLUMNS = new DataKey<>("APPEND_MISSING_COLUMNS", false);
    public static final DataKey<Boolean> DISCARD_EXTRA_COLUMNS = new DataKey<>("DISCARD_EXTRA_COLUMNS", false);
    public static final DataKey<Boolean> TRIM_CELL_WHITESPACE = new DataKey<>("TRIM_CELL_WHITESPACE", true);
    public static final DataKey<Boolean> COLUMN_SPANS = new DataKey<>("COLUMN_SPANS", true);
    public static final DataKey<Boolean> HEADER_SEPARATOR_COLUMN_MATCH = new DataKey<>("HEADER_SEPARATOR_COLUMN_MATCH", false);
    public static final DataKey<String> CLASS_NAME = new DataKey<>("CLASS_NAME", "");
    public static final DataKey<Boolean> WITH_CAPTION = new DataKey<>("WITH_CAPTION", true);

    // format options copy from TableFormatOptions for convenience
    public static final DataKey<Boolean> FORMAT_LEAD_TRAIL_PIPES = TableFormatOptions.LEAD_TRAIL_PIPES;
    public static final DataKey<Boolean> FORMAT_SPACE_AROUND_PIPES = TableFormatOptions.SPACE_AROUND_PIPES;
    public static final DataKey<Boolean> FORMAT_ADJUST_COLUMN_WIDTH = TableFormatOptions.ADJUST_COLUMN_WIDTH;
    public static final DataKey<Boolean> FORMAT_APPLY_COLUMN_ALIGNMENT = TableFormatOptions.APPLY_COLUMN_ALIGNMENT;
    public static final DataKey<Boolean> FORMAT_FILL_MISSING_COLUMNS = TableFormatOptions.FILL_MISSING_COLUMNS;
    public static final DataKey<Boolean> FORMAT_REMOVE_CAPTION = TableFormatOptions.REMOVE_CAPTION;
    public static final DataKey<DiscretionaryText> FORMAT_LEFT_ALIGN_MARKER = TableFormatOptions.LEFT_ALIGN_MARKER;
    public static final DataKey<Integer> FORMAT_MIN_SEPARATOR_COLUMN_WIDTH = TableFormatOptions.MIN_SEPARATOR_COLUMN_WIDTH;
    public static final DataKey<Integer> FORMAT_MIN_SEPARATOR_DASHES = TableFormatOptions.MIN_SEPARATOR_DASHES;
    public static final DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = TableFormatOptions.CHAR_WIDTH_PROVIDER;

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
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new TableNodeRenderer.Factory());
                break;

            case "JIRA":
            case "YOUTRACK":
                rendererBuilder.nodeRendererFactory(new TableJiraRenderer.Factory());
                break;
        }
    }
}
