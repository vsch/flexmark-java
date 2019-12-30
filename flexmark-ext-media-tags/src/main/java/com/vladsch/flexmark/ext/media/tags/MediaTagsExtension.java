package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ext.media.tags.internal.MediaTagsNodePostProcessor;
import com.vladsch.flexmark.ext.media.tags.internal.MediaTagsNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

public class MediaTagsExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private MediaTagsExtension() {
    }

    public static MediaTagsExtension create() {
        return new MediaTagsExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new MediaTagsNodePostProcessor.Factory(parserBuilder));
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
            htmlRendererBuilder.nodeRendererFactory(new MediaTagsNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
