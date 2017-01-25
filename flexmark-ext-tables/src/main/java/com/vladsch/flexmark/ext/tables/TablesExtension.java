package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.tables.internal.TableJiraRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableParagraphPreProcessor;
import com.vladsch.flexmark.formatter.options.DiscretionaryText;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
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
public class TablesExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Integer> MAX_HEADER_ROWS = new DataKey<>("MAX_HEADER_ROWS", Integer.MAX_VALUE);
    public static final DataKey<Integer> MIN_HEADER_ROWS = new DataKey<>("MIN_HEADER_ROWS", 0);
    public static final DataKey<Boolean> APPEND_MISSING_COLUMNS = new DataKey<>("APPEND_MISSING_COLUMNS", false);
    public static final DataKey<Boolean> DISCARD_EXTRA_COLUMNS = new DataKey<>("DISCARD_EXTRA_COLUMNS", false);
    public static final DataKey<Boolean> TRIM_CELL_WHITESPACE = new DataKey<>("TRIM_CELL_WHITESPACE", true);
    public static final DataKey<Boolean> COLUMN_SPANS = new DataKey<>("COLUMN_SPANS", true);
    public static final DataKey<Boolean> HEADER_SEPARATOR_COLUMN_MATCH = new DataKey<>("HEADER_SEPARATOR_COLUMN_MATCH", false);
    public static final DataKey<String> CLASS_NAME = new DataKey<>("CLASS_NAME", "");
    public static final DataKey<Boolean> WITH_CAPTION = new DataKey<>("WITH_CAPTION", true);

    // format options
    public static final DataKey<Boolean> FORMAT_LEAD_TRAIL_PIPES = new DataKey<>("FORMAT_LEAD_TRAIL_PIPES", true);
    public static final DataKey<Boolean> FORMAT_SPACE_AROUND_PIPE = new DataKey<>("FORMAT_SPACE_AROUND_PIPE", true);
    public static final DataKey<Boolean> FORMAT_ADJUST_COLUMN_WIDTH = new DataKey<>("FORMAT_ADJUST_COLUMN_WIDTH", true);
    public static final DataKey<Boolean> FORMAT_APPLY_COLUMN_ALIGNMENT = new DataKey<>("FORMAT_APPLY_COLUMN_ALIGNMENT", true);
    public static final DataKey<Boolean> FORMAT_FILL_MISSING_COLUMNS = new DataKey<>("FORMAT_FILL_MISSING_COLUMNS", true);
    public static final DataKey<Boolean> FORMAT_DELETE_EMPTY_COLUMNS = new DataKey<>("FORMAT_DELETE_EMPTY_COLUMNS", true);
    public static final DataKey<Boolean> FORMAT_DELETE_EMPTY_ROWS = new DataKey<>("FORMAT_DELETE_EMPTY_ROWS", true);
    public static final DataKey<Boolean> FORMAT_TRIM_CELLS = new DataKey<>("FORMAT_TRIM_CELLS", false);
    public static final DataKey<DiscretionaryText> FORMAT_LEFT_ALIGN_MARKER = new DataKey<>("FORMAT_LEFT_ALIGN_MARKER", DiscretionaryText.AS_IS);

    public static Extension create() {
        return new TablesExtension();
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
                rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                    @Override
                    public NodeRenderer create(DataHolder options) {
                        return new TableNodeRenderer(options);
                    }
                });
                break;

            case "JIRA":
            case "YOUTRACK":
                rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                    @Override
                    public NodeRenderer create(DataHolder options) {
                        return new TableJiraRenderer(options);
                    }
                });
                break;
        }
    }
}
