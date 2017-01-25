package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.tables.internal.TableBlockParser;
import com.vladsch.flexmark.ext.gfm.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
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

    private TablesExtension() {
    }

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
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new TableNodeRenderer.Factory());
                break;

            case "JIRA":
            case "YOUTRACK":
                break;
        }
    }
}
