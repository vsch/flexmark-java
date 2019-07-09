package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.html2md.converter.internal.HtmlConverterCoreNodeRenderer;
import com.vladsch.flexmark.util.data.DataHolder;

public class HtmlConverterCoreNodeRendererFactory implements HtmlNodeRendererFactory {
    @Override
    public HtmlNodeRenderer apply(DataHolder options) {
        return new HtmlConverterCoreNodeRenderer(options);
    }
}
