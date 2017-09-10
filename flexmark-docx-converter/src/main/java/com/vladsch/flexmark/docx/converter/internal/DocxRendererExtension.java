package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.util.options.MutableDataHolder;

public class DocxRendererExtension implements DocxRenderer.RendererExtension {
    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(final DocxRenderer.Builder builder) {
        builder.linkResolverFactory(new DocxLinkResolver.Factory());
    }

    public static Extension create() {
        return new DocxRendererExtension();
    }
}
