package com.vladsch.flexmark.ext.ins;

import com.vladsch.flexmark.ext.ins.internal.InsDelimiterProcessor;
import com.vladsch.flexmark.ext.ins.internal.InsJiraRenderer;
import com.vladsch.flexmark.ext.ins.internal.InsNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for ins
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed ins text is turned into {@link Ins} nodes.
 */
public class InsExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static NullableDataKey<String> INS_STYLE_HTML_OPEN = new NullableDataKey<>("INS_STYLE_HTML_OPEN");
    final public static NullableDataKey<String> INS_STYLE_HTML_CLOSE = new NullableDataKey<>("INS_STYLE_HTML_CLOSE");

    private InsExtension() {
    }

    public static InsExtension create() {
        return new InsExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new InsDelimiterProcessor());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new InsNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new InsJiraRenderer.Factory());
        }
    }
}
