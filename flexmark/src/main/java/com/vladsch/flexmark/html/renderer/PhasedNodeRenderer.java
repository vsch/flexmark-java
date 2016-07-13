package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.node.Document;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface PhasedNodeRenderer extends NodeRenderer {

    Set<RenderingPhase> getRenderingPhases();

    /**
     * Render the specified node.
     *  @param context
     * @param html
     * @param document the document node to render
     */
    void renderDocument(NodeRendererContext context, HtmlWriter html, Document document, RenderingPhase phase);
}
