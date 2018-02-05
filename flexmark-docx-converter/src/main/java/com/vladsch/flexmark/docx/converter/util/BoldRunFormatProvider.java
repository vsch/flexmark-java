package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.docx.converter.internal.DocxRenderer;
import org.docx4j.wml.RPr;

public class BoldRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public BoldRunFormatProvider(final DocxContext<T> docx, boolean noCharacterStyles) {
        super(docx, docx.getRenderingOptions().BOLD_STYLE, noCharacterStyles, null);
    }

    @Override
    public void getRPr(final RPr rPr) {
        super.getRPr(rPr);
        //rPr.setB(docx.getBooleanDefaultTrue());
        //rPr.setBCs(docx.getBooleanDefaultTrue());
    }
}
