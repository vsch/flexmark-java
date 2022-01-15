package com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage;

import com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage.internal.ResizableImageInlineParserExtension;
import com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage.internal.ResizableImageNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;

import org.jetbrains.annotations.NotNull;

public class ResizableImageExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private ResizableImageExtension() {
    }

    public static ResizableImageExtension create() {
        return new ResizableImageExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customInlineParserExtensionFactory(new ResizableImageInlineParserExtension.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new ResizableImageNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            return;
        }
    }
}
