package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.RPr;

public class AttributeRunFormatProvider<T> extends RunFormatProviderBase<T> {
    final public AttributeFormat myAttributeFormat;

    public AttributeRunFormatProvider(DocxContext<T> docx, String fontFamily, String fontSize, String fontWeight, String fontStyle, String textColor, String fillColor) {
        super(docx, null, false, null);
        myAttributeFormat = new AttributeFormat(fontFamily, fontSize, fontWeight, fontStyle, textColor, fillColor);
    }

    public AttributeRunFormatProvider(DocxContext<T> docx, AttributeFormat attributeFormat) {
        super(docx, null, false, null);
        myAttributeFormat = attributeFormat;
    }

    @Override
    public void getRPr(RPr rPr) {
        RunFormatProvider<T> parent = myParent;
        if (parent != null) {
            RPr rpr1 = myDocx.getFactory().createRPr();
            parent.getRPr(rpr1);

            inheritParentStyle(rPr, rpr1);
        }

        ParaRPr paraRPr = myDocx.getP().getPPr().getRPr();

        myAttributeFormat.setFormatRPr(rPr, myDocx);
        myDocx.getHelper().keepDiff(rPr, paraRPr);
    }
}
