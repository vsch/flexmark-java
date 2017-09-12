package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class UnderlineRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public UnderlineRunFormatProvider(final DocxContext<T> docx) {
        super(docx, RunFormatProvider.INS_STYLE);
    }

    @Override
    public void getRPr(final RPr rPr) {
        super.getRPr(rPr);
        //U u = docx.getFactory().createU();
        //rPr.setU(u);
        //u.setVal(org.docx4j.wml.UnderlineEnumeration.SINGLE);
    }
}
