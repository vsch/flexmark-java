package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.CTShd;
import org.docx4j.wml.PPr;

public class AttributeBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    final public AttributeFormat myAttributeFormat;

    public AttributeBlockFormatProvider(DocxContext<T> docx, String fontFamily, String fontSize, String fontWeight, String fontStyle, String textColor, String fillColor) {
        super(docx, null);
        myAttributeFormat = new AttributeFormat(fontFamily, fontSize, fontWeight, fontStyle, textColor, fillColor);
    }

    public AttributeBlockFormatProvider(DocxContext<T> docx, AttributeFormat attributeFormat) {
        super(docx, null);
        myAttributeFormat = attributeFormat;
    }

    @Override
    public void open() {
        super.close();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void adjustPPrForFormatting(PPr pPr) {
        CTShd shd = myAttributeFormat.getShd(myDocx);
        pPr.setShd(shd);
    }
}
