package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.ext.gfm.tables.internal.TableBlockParser;
import com.vladsch.flexmark.ext.gfm.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for GFM tables using "|" pipes (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed tables are turned into {@link TableBlock} blocks.
 * </p>
 *
 * @deprecated this module is not maintained or updated, use flexmark-ext-tables extension which can
 * be configured for GFM table compatibility and has more options
 */
public class TablesExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private TablesExtension() {
    }

    public static TablesExtension create() {
        return new TablesExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new TableNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
        }
    }
}
