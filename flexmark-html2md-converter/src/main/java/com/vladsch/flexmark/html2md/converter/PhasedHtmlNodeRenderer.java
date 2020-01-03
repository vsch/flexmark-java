package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.sequence.LineAppendable;
import org.jsoup.nodes.Document;

import java.util.Set;

/**
 * A renderer for a document node for a specific rendering phase
 */
public interface PhasedHtmlNodeRenderer extends HtmlNodeRenderer {
    Set<HtmlConverterPhase> getHtmlConverterPhases();

    /**
     * Render the specified node.
     *
     * @param context  node renderer context instance
     * @param markdown markdown writer instance
     * @param document the document node to render
     * @param phase    rendering phase for which to generate the output. Will be any of {@link HtmlConverterPhase} except {@link HtmlConverterPhase#DOCUMENT} because this phase is used for the non-phased node rendering
     */
    void renderDocument(HtmlNodeConverterContext context, LineAppendable markdown, Document document, HtmlConverterPhase phase);
}
