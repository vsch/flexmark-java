package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.html.HtmlWriter;

import java.util.Set;

/**
 * A renderer for a document node for a specific rendering phase
 */
public interface PhasedNodeRenderer extends NodeRenderer {

    Set<RenderingPhase> getRenderingPhases();

    /**
     * Render the specified node.
     *
     * @param context  node renderer context instance
     * @param html     html writer instance
     * @param document the document node to render
     * @param phase    rendering phase for which to generate the output. Will be any of {@link RenderingPhase} except {@link RenderingPhase#BODY} because this phase is used for the non-phased node rendering
     */
    void renderDocument(NodeRendererContext context, HtmlWriter html, Document document, RenderingPhase phase);
}
