package com.vladsch.flexmark.ext.youtube.embedded;

import com.vladsch.flexmark.ext.youtube.embedded.internal.YouTubeLinkNodePostProcessor;
import com.vladsch.flexmark.ext.youtube.embedded.internal.YouTubeLinkNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

public class YouTubeLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private YouTubeLinkExtension() {
    }

    public static YouTubeLinkExtension create() {
        return new YouTubeLinkExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new YouTubeLinkNodePostProcessor.Factory(parserBuilder));
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new YouTubeLinkNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
