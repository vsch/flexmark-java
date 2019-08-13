package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class SourceCodeRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public SourceCodeRunFormatProvider(DocxContext<T> docx, boolean noCharacterStyles, String useHighlightShading) {
        super(docx, docx.getRenderingOptions().INLINE_CODE_STYLE, noCharacterStyles, useHighlightShading);
    }

    @Override
    public void getRPr(RPr rPr) {
        super.getRPr(rPr);
    }
}
