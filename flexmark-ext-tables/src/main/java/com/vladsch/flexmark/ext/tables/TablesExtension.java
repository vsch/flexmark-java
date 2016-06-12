package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.tables.internal.TableBlockParser;
import com.vladsch.flexmark.ext.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.internal.util.Options;
import com.vladsch.flexmark.internal.util.PropertyKey;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for GFM tables using "|" pipes (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create(Options)} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed tables are turned into {@link TableBlock} blocks.
 * </p>
 */
public class TablesExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final static public PropertyKey<Integer> MAX_HEADER_ROWS = new PropertyKey<>("TABLE.MAX_HEADER_ROWS", Integer.MAX_VALUE);
    final static public PropertyKey<Boolean> APPEND_MISSING_COLUMNS = new PropertyKey<>("TABLE.APPEND_MISSING_COLUMNS", false);
    final static public PropertyKey<Boolean> DISCARD_EXTRA_COLUMNS = new PropertyKey<>("TABLE.DISCARD_EXTRA_COLUMNS", false);
    final static public PropertyKey<Boolean> COLUMN_SPANS = new PropertyKey<>("TABLE.COLUMN_SPANS", true);

    private final Options options;

    public TablesExtension(Options options) {
        this.options = options;
    }

    public static Extension create(Options options) {
        return new TablesExtension(options);
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory(options));
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(NodeRendererContext context) {
                return new TableNodeRenderer(context, options);
            }
        });
    }
}
