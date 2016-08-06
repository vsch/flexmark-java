package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.html.HtmlWriter;

import java.util.Set;

/**
 * A renderer for a set of ast types.
 */
public interface PhasedNodeRenderer extends NodeRenderer {

    Set<RenderingPhase> getRenderingPhases();

    /**
     * Render the specified ast.
     *  @param context
     * @param html
     * @param document the document ast to render
     */
    void renderDocument(NodeRendererContext context, HtmlWriter html, Document document, RenderingPhase phase);
}
