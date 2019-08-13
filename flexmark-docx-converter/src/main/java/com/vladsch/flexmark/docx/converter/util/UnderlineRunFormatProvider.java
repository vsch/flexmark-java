package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class UnderlineRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public UnderlineRunFormatProvider(DocxContext<T> docx, boolean noCharacterStyles) {
        super(docx, docx.getRenderingOptions().INS_STYLE, noCharacterStyles, null);
    }

    @Override
    public void getRPr(RPr rPr) {
        super.getRPr(rPr);
        //U u = docx.getFactory().createU();
        //rPr.setU(u);
        //u.setVal(org.docx4j.wml.UnderlineEnumeration.SINGLE);
    }
}
