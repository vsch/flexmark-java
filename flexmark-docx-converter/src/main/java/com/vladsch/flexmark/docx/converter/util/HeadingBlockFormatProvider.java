package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;

import java.math.BigInteger;

public class HeadingBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    private final DocxContext<T> myDocx;
    private final int myHeadingLevel;

    public HeadingBlockFormatProvider(final DocxContext<T> docx, final int headingLevel) {
        super(docx, docx.getRenderingOptions().HEADINGS[headingLevel]);
        myDocx = docx;
        myHeadingLevel = headingLevel;
    }

    @Override
    public void getPPr(final PPr pPr) {
        // Create object for numPr
        PPrBase.NumPr baseNumPr = myDocx.getFactory().createPPrBaseNumPr();
        pPr.setNumPr(baseNumPr);

        // Create object for numId
        PPrBase.NumPr.NumId prNumId = myDocx.getFactory().createPPrBaseNumPrNumId();
        baseNumPr.setNumId(prNumId);
        prNumId.setVal(BigInteger.valueOf(0));

        // Create object for ilvl
        PPrBase.NumPr.Ilvl prIlvl = myDocx.getFactory().createPPrBaseNumPrIlvl();
        baseNumPr.setIlvl(prIlvl);
        prIlvl.setVal(BigInteger.valueOf(myHeadingLevel));

        // handle inheritance
        super.getPPr(pPr);
    }
}
