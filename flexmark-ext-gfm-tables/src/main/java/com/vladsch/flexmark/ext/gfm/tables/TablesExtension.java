package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.tables.internal.TableBlockParser;
import com.vladsch.flexmark.ext.gfm.tables.internal.TableNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

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
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new TableNodeRenderer(options);
                }
            });
        }
    }
}
