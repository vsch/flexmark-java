package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.util.ast.Document;

import java.util.Set;

/**
 * A renderer for a document node for a specific rendering phase
 */
public interface PhasedNodeDocxRenderer extends NodeDocxRenderer {
    Set<DocxRendererPhase> getFormattingPhases();

    /**
     * Render the specified node.
     *
     * @param docx     docx renderer context instance
     * @param document the document node to render
     * @param phase    rendering phase for which to generate the output. Will be any of {@link DocxRendererPhase} except {@link DocxRendererPhase#DOCUMENT} because this phase is used for the non-phased node rendering
     */
    void renderDocument(DocxRendererContext docx, Document document, DocxRendererPhase phase);
}
