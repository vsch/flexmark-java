package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.docx.converter.internal.DocxRenderer;
import org.docx4j.wml.RPr;

public class SourceCodeRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public SourceCodeRunFormatProvider(final DocxContext<T> docx, boolean noCharacterStyles, String useHighlightShading) {
        super(docx, docx.getRenderingOptions().INLINE_CODE_STYLE, noCharacterStyles, useHighlightShading);
    }

    @Override
    public void getRPr(final RPr rPr) {
        super.getRPr(rPr);
    }
}
