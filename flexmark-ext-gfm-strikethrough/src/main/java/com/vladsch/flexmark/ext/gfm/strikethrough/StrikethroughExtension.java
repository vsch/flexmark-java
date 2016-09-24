package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughDelimiterProcessor;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughJiraRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for GFM strikethrough using ~~ (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed strikethrough text regions are turned into {@link Strikethrough} nodes.
 * </p>
 */
public class StrikethroughExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private StrikethroughExtension() {
    }

    public static Extension create() {
        return new StrikethroughExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new StrikethroughDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA")) {
            rendererBuilder.nodeRendererFactory(StrikethroughJiraRenderer::new);
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(StrikethroughNodeRenderer::new);
        }
    }
}
