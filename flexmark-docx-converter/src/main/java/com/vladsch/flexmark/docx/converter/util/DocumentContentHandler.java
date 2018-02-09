package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.docx.converter.DocxRendererContext;

public interface DocumentContentHandler extends ContentContainer {
    /**
     * Called before starting document rendering
     *
     * @param context rendering context
     */
    void startDocumentRendering(DocxRendererContext context);

    /**
     * Called after finishing document rendering
     *
     * @param context rendering context
     */
    void endDocumentRendering(DocxRendererContext context);
}
