package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class BoldRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public BoldRunFormatProvider(final DocxContext<T> docx, boolean noCharacterStyles) {
        super(docx, RunFormatProvider.BOLD_STYLE, noCharacterStyles);
    }

    @Override
    public void getRPr(final RPr rPr) {
        super.getRPr(rPr);
        //rPr.setB(docx.getBooleanDefaultTrue());
        //rPr.setBCs(docx.getBooleanDefaultTrue());
    }
}
