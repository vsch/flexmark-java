package com.vladsch.flexmark.ext.superscript;

import com.vladsch.flexmark.ext.superscript.internal.SuperscriptDelimiterProcessor;
import com.vladsch.flexmark.ext.superscript.internal.SuperscriptJiraRenderer;
import com.vladsch.flexmark.ext.superscript.internal.SuperscriptNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for superscripts
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed superscript text is turned into {@link Superscript} nodes.
 */
public class SuperscriptExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static NullableDataKey<String> SUPERSCRIPT_STYLE_HTML_OPEN = new NullableDataKey<>("SUPERSCRIPT_STYLE_HTML_OPEN");
    final public static NullableDataKey<String> SUPERSCRIPT_STYLE_HTML_CLOSE = new NullableDataKey<>("SUPERSCRIPT_STYLE_HTML_CLOSE");

    private SuperscriptExtension() {
    }

    public static SuperscriptExtension create() {
        return new SuperscriptExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new SuperscriptDelimiterProcessor());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new SuperscriptNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new SuperscriptJiraRenderer.Factory());
        }
    }
}
