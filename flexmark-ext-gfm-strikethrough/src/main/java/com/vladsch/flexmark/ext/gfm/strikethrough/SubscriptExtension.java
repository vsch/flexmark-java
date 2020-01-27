package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughJiraRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughNodeRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughYouTrackRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.SubscriptDelimiterProcessor;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for GFM strikethrough using ~~ (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * </p>
 * <p>
 * The parsed strikethrough text regions are turned into {@link Strikethrough} nodes.
 * </p>
 */
public class SubscriptExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static NullableDataKey<String> SUBSCRIPT_STYLE_HTML_OPEN = StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_OPEN;
    final public static NullableDataKey<String> SUBSCRIPT_STYLE_HTML_CLOSE = StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_CLOSE;

    private SubscriptExtension() {
    }

    public static SubscriptExtension create() {
        return new SubscriptExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new SubscriptDelimiterProcessor());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new StrikethroughNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("YOUTRACK")) {
            htmlRendererBuilder.nodeRendererFactory(new StrikethroughYouTrackRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new StrikethroughJiraRenderer.Factory());
        }
    }
}
