package com.vladsch.flexmark.html2md.converter;

import java.util.List;

/**
 * A renderer for a set of node types.
 */
public interface HtmlNodeRenderer {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    List<HtmlNodeRendererHandler<?>> getHtmlNodeRendererHandlers();
}
