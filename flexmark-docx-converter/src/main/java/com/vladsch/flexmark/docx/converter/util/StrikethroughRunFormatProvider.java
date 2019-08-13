package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class StrikethroughRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public StrikethroughRunFormatProvider(DocxContext<T> docx, boolean noCharacterStyles) {
        super(docx, docx.getRenderingOptions().STRIKE_THROUGH_STYLE, noCharacterStyles, null);
    }

    @Override
    public void getRPr(RPr rPr) {
        super.getRPr(rPr);
        //rPr.setStrike(docx.getFactory().createBooleanDefaultTrue());
    }
}
