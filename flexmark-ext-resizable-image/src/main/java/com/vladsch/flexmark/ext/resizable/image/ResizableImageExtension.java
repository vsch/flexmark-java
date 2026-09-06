package com.vladsch.flexmark.ext.resizable.image;

import com.vladsch.flexmark.ext.resizable.image.internal.ResizableImageInlineParserExtension;
import com.vladsch.flexmark.ext.resizable.image.internal.ResizableImageNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;


public class ResizableImageExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private ResizableImageExtension() {
    }

    public static ResizableImageExtension create() {
        return new ResizableImageExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customInlineParserExtensionFactory(new ResizableImageInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder htmlRendererBuilder, String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new ResizableImageNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            return;
        }
    }
}
