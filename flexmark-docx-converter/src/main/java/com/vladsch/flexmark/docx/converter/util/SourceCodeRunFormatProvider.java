package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class SourceCodeRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public SourceCodeRunFormatProvider(final DocxContext<T> docx, boolean noCharacterStyles) {
        super(docx, RunFormatProvider.INLINE_CODE_STYLE, noCharacterStyles);
    }

    @Override
    public void getRPr(final RPr rPr) {
        super.getRPr(rPr);
    }
}
