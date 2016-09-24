package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.tables.internal.TableJiraRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.ext.tables.internal.TableParagraphPreProcessor;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;

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
    final static public DataKey<Integer> MAX_HEADER_ROWS = new DataKey<>("MAX_HEADER_ROWS", Integer.MAX_VALUE);
    final static public DataKey<Integer> MIN_HEADER_ROWS = new DataKey<>("MIN_HEADER_ROWS", 0);
    final static public DataKey<Boolean> APPEND_MISSING_COLUMNS = new DataKey<>("APPEND_MISSING_COLUMNS", false);
    final static public DataKey<Boolean> DISCARD_EXTRA_COLUMNS = new DataKey<>("DISCARD_EXTRA_COLUMNS", false);
    final static public DataKey<Boolean> TRIM_CELL_WHITESPACE = new DataKey<>("TRIM_CELL_WHITESPACE", true);
    final static public DataKey<Boolean> COLUMN_SPANS = new DataKey<>("COLUMN_SPANS", true);
    final static public DataKey<Boolean> HEADER_SEPARATOR_COLUMN_MATCH = new DataKey<>("HEADER_SEPARATOR_COLUMN_MATCH", false);
    final static public DataKey<String> CLASS_NAME = new DataKey<>("CLASS_NAME", "");

    public static Extension create() {
        return new TablesExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.paragraphPreProcessorFactory(TableParagraphPreProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA")) {
            rendererBuilder.nodeRendererFactory(TableJiraRenderer::new);
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(TableNodeRenderer::new);
        }
    }
}
