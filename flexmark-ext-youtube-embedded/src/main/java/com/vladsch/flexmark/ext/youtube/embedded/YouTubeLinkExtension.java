package com.vladsch.flexmark.ext.youtube.embedded;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.youtube.embedded.internal.YouTubeLinkNodePostProcessor;
import com.vladsch.flexmark.ext.youtube.embedded.internal.YouTubeLinkNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

public class YouTubeLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private YouTubeLinkExtension() {
    }

    public static Extension create() {
        return new YouTubeLinkExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new YouTubeLinkNodePostProcessor.Factory(parserBuilder));
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new YouTubeLinkNodeRenderer.Factory());
        } else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        }
    }
}
