package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class SubscriptRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public SubscriptRunFormatProvider(final DocxContext<T> docx) {
        super(docx, RunFormatProvider.SUBSCRIPT_STYLE);
    }

    @Override
    public void getRPr(final RPr rPr) {
        super.getRPr(rPr);
        //
        //// Create object for sz
        //HpsMeasure hpsmeasure = docx.getFactory().createHpsMeasure();
        //rPr.setSz(hpsmeasure);
        //hpsmeasure.setVal(BigInteger.valueOf(19));
        //
        //// Create object for position
        //CTSignedHpsMeasure signedhpsmeasure = docx.getFactory().createCTSignedHpsMeasure();
        //rPr.setPosition(signedhpsmeasure);
        //signedhpsmeasure.setVal(BigInteger.valueOf(-4));
    }
}
