package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.ext.media.tags.internal.MediaTagsNodePostProcessor;
import com.vladsch.flexmark.ext.media.tags.internal.MediaTagsNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.MutableDataHolder;

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
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new MediaTagsNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
        }
    }
}
