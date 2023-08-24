package com.vladsch.flexmark.ext.d2;

import com.vladsch.flexmark.ext.d2.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;

import org.jetbrains.annotations.NotNull;

/**
 * Extension for d2 diagrams
 */
public class D2Extension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private D2Extension() {
    }

    public static D2Extension create() {
        return new D2Extension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new D2BlockParser.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML") || htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new D2NodeRenderer.Factory());
        }
    }
}
