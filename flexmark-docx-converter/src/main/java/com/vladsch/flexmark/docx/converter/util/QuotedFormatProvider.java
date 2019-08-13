package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.ast.Paragraph;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.PPr;
import org.docx4j.wml.Style;

import java.math.BigInteger;

public class QuotedFormatProvider<T> extends BlockFormatProviderBase<T> {
    private final BigInteger myBefore;
    private final BigInteger myAfter;
    private final BigInteger myLeftInd;

    public QuotedFormatProvider(final DocxContext<T> docx, int level, final String styleId) {
        super(docx, styleId);

        final BigInteger left;
        final BigInteger right;
        final BigInteger before;
        final BigInteger after;

        final Style style = docx.getStyle(styleId);
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

        final BigInteger quoteLevel = BigInteger.valueOf(level);
        final BigInteger leftInd = left.multiply(quoteLevel);

        myBefore = before;
        myAfter = after;
        myLeftInd = leftInd;
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
        // here we need to adjust for inherited left margin
        final BigInteger newLeftInd = myDocx.getHelper().safeIndLeft(pPr);
        final PPr styledPPr = myDocx.getHelper().getExplicitPPr(pPr);
        if (styledPPr != null && styledPPr.getPBdr() != null && newLeftInd != null && newLeftInd.compareTo(myLeftInd) > 0) {
            // it grew, word has the border hanging and we want to shift it by our left border spacing
            CTBorder leftBorder = styledPPr.getPBdr().getLeft();
            if (leftBorder != null && leftBorder.getSpace() != null && leftBorder.getSpace().compareTo(BigInteger.ZERO) > 0) {
                //pPr.getInd().setLeft(newLeftInd.add(leftBorder.getSpace().multiply(BigInteger.valueOf(20))));

                final T currentNode = myDocx.getContextFrame();
                if (currentNode instanceof Paragraph) {
                    int tmp = 0;
                }
            }
        }
    }
}
