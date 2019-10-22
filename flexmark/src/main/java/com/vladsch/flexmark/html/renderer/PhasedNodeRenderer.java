package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.ast.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * A renderer for a document node for a specific rendering phase
 */
public interface PhasedNodeRenderer extends NodeRenderer {

    @Nullable Set<RenderingPhase> getRenderingPhases();

    /**
     * Render the specified node.
     *
     * @param context  node renderer context instance
     * @param html     html writer instance
     * @param document the document node to render
     * @param phase    rendering phase for which to generate the output. Will be any of {@link RenderingPhase}
     *                 no rendering should be done if phase is {@link RenderingPhase#BODY} because this phase
     *                 is used for the non-phased node rendering. For body phase this method is called before
     *                 the node renderer calls are made so it is a good place to reset internal structures for
     *                 start of each phase.
     */
    void renderDocument(@NotNull NodeRendererContext context, @NotNull HtmlWriter html, @NotNull Document document, @NotNull RenderingPhase phase);
}
