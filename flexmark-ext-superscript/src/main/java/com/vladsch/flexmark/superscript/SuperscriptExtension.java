package com.vladsch.flexmark.superscript;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.internal.SuperscriptDelimiterProcessor;
import com.vladsch.flexmark.superscript.internal.SuperscriptJiraRenderer;
import com.vladsch.flexmark.superscript.internal.SuperscriptNodeRenderer;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for superscripts
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed superscript text is turned into {@link Superscript} nodes.
 */
public class SuperscriptExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<String> SUPERSCRIPT_STYLE_HTML_OPEN = new DataKey<>("SUPERSCRIPT_STYLE_HTML_OPEN", (String) null);
    public static final DataKey<String> SUPERSCRIPT_STYLE_HTML_CLOSE = new DataKey<>("SUPERSCRIPT_STYLE_HTML_CLOSE", (String) null);

    private SuperscriptExtension() {
    }

    public static Extension create() {
        return new SuperscriptExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new SuperscriptDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new SuperscriptNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new SuperscriptJiraRenderer.Factory());
        }
    }
}
