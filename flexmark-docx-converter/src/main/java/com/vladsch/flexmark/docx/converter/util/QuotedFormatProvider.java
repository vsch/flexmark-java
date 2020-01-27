package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;
import org.docx4j.wml.Style;

import java.math.BigInteger;

public class QuotedFormatProvider<T> extends BlockFormatProviderBase<T> {
    final private BigInteger myBefore;
    final private BigInteger myAfter;

    public QuotedFormatProvider(DocxContext<T> docx, int level, String styleId) {
        super(docx, styleId);

        BigInteger left;
        BigInteger before;
        BigInteger after;

        Style style = docx.getStyle(styleId);
        if (style != null) {
            // Should always be true
            left = docx.getHelper().safeIndLeft(style.getPPr(), 240);
            before = docx.getHelper().safeSpacingBefore(style.getPPr());
            after = docx.getHelper().safeSpacingAfter(style.getPPr());
        } else {
            left = BigInteger.valueOf(240);
            before = BigInteger.ZERO;
            after = BigInteger.ZERO;
        }

        BigInteger quoteLevel = BigInteger.valueOf(level);
        BigInteger leftInd = left.multiply(quoteLevel);

        myBefore = before;
        myAfter = after;
    }

    @Override
    public void open() {
        super.close();
        myDocx.addBlankLine(myBefore, myDocx.getRenderingOptions().DEFAULT_STYLE);
    }

    @Override
    public void close() {
        myDocx.addBlankLine(myAfter, myDocx.getRenderingOptions().DEFAULT_STYLE);
        super.close();
    }

    @Override
    public void adjustPPrForFormatting(PPr pPr) {
//        // here we need to adjust for inherited left margin
//        BigInteger newLeftInd = myDocx.getHelper().safeIndLeft(pPr);
//        PPr styledPPr = myDocx.getHelper().getExplicitPPr(pPr);
//        if (styledPPr != null && styledPPr.getPBdr() != null && newLeftInd != null && newLeftInd.compareTo(myLeftInd) > 0) {
//            // it grew, word has the border hanging and we want to shift it by our left border spacing
//            CTBorder leftBorder = styledPPr.getPBdr().getLeft();
//            if (leftBorder != null && leftBorder.getSpace() != null && leftBorder.getSpace().compareTo(BigInteger.ZERO) > 0) {
//                //pPr.getInd().setLeft(newLeftInd.add(leftBorder.getSpace().multiply(BigInteger.valueOf(20))));
//                //
//                //T currentNode = myDocx.getContextFrame();
//                //if (currentNode instanceof Paragraph) {
//                //    int tmp = 0;
//                //}
//            }
//        }
    }
}
