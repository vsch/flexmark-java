package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughJiraRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughNodeRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughSubscriptDelimiterProcessor;
import com.vladsch.flexmark.ext.gfm.strikethrough.internal.StrikethroughYouTrackRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for GFM strikethrough using ~~ (GitHub Flavored Markdown).
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed strikethrough text regions are turned into {@link Strikethrough} nodes.
 */
public class StrikethroughSubscriptExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<String> STRIKETHROUGH_STYLE_HTML_OPEN = new DataKey<>("STRIKETHROUGH_STYLE_HTML_OPEN", (String) null);
    public static final DataKey<String> STRIKETHROUGH_STYLE_HTML_CLOSE = new DataKey<>("STRIKETHROUGH_STYLE_HTML_CLOSE", (String) null);
    public static final DataKey<String> SUBSCRIPT_STYLE_HTML_OPEN = new DataKey<>("SUBSCRIPT_STYLE_HTML_OPEN", (String) null);
    public static final DataKey<String> SUBSCRIPT_STYLE_HTML_CLOSE = new DataKey<>("SUBSCRIPT_STYLE_HTML_CLOSE", (String) null);

    private StrikethroughSubscriptExtension() {
    }

    public static StrikethroughSubscriptExtension create() {
        return new StrikethroughSubscriptExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new StrikethroughSubscriptDelimiterProcessor());
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
