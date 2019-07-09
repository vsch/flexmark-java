package com.vladsch.flexmark.html2md.converter;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface HtmlNodeRenderer {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    Set<HtmlNodeRendererHandler<?>> getHtmlNodeRendererHandlers();
}
