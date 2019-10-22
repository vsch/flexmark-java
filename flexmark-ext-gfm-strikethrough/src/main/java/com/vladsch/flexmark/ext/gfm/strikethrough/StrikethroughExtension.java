package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughDelimiterProcessor;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughJiraRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughNodeRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughYouTrackRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for GFM strikethrough using ~~ (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed strikethrough text regions are turned into {@link Strikethrough} nodes.
 */
public class StrikethroughExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final NullableDataKey<String> STRIKETHROUGH_STYLE_HTML_OPEN = StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_OPEN;
    public static final NullableDataKey<String> STRIKETHROUGH_STYLE_HTML_CLOSE = StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_CLOSE;

    private StrikethroughExtension() {
    }

    public static StrikethroughExtension create() {
        return new StrikethroughExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new StrikethroughDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new StrikethroughNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("YOUTRACK")) {
            rendererBuilder.nodeRendererFactory(new StrikethroughYouTrackRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new StrikethroughJiraRenderer.Factory());
        }
    }
}
