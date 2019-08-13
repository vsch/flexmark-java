package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class ItalicRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public ItalicRunFormatProvider(DocxContext<T> docx, boolean noCharacterStyles) {
        super(docx, docx.getRenderingOptions().ITALIC_STYLE, noCharacterStyles, null);
    }

    @Override
    public void getRPr(RPr rPr) {
        super.getRPr(rPr);
        //rPr.setI(docx.getBooleanDefaultTrue());
        //rPr.setICs(docx.getBooleanDefaultTrue());
    }
}
