package com.vladsch.flexmark.ext.highlight;

import com.vladsch.flexmark.ext.highlight.internal.HighlightDelimiterProcessor;
import com.vladsch.flexmark.ext.highlight.internal.HighlightNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for highlight (HTML <code>mark</code> element).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed highlight text is turned into {@link Highlight} nodes.
 */
public class HighlightExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static NullableDataKey<String> HIGHLIGHT_STYLE_HTML_OPEN = new NullableDataKey<>("HIGHLIGHT_STYLE_HTML_OPEN");
    final public static NullableDataKey<String> HIGHLIGHT_STYLE_HTML_CLOSE = new NullableDataKey<>("HIGHLIGHT_STYLE_HTML_CLOSE");

    private HighlightExtension() {
    }

    public static HighlightExtension create() {
        return new HighlightExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new HighlightDelimiterProcessor());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new HighlightNodeRenderer.Factory());
        }
    }
}
