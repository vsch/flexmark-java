package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.Style;

import java.math.BigInteger;

public class FencedCodeBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    final private DocxContext<T> myDocx;
    final private BigInteger myBefore;
    final private BigInteger myAfter;

    public FencedCodeBlockFormatProvider(DocxContext<T> docx) {
        super(docx, docx.getRenderingOptions().PREFORMATTED_TEXT_STYLE);
        BigInteger before;
        BigInteger after;

        Style paragraphStyle = docx.getStyle(docx.getRenderingOptions().PREFORMATTED_TEXT_STYLE);
        if (paragraphStyle != null) {
            // Should always be true
            before = docx.getHelper().safeSpacingBefore(paragraphStyle.getPPr());
            after = docx.getHelper().safeSpacingAfter(paragraphStyle.getPPr());
        } else {
            before = BigInteger.ZERO;
            after = BigInteger.ZERO;
        }

        myDocx = docx;
        myBefore = before;
        myAfter = after;
    }

    @Override
    public void open() {
        super.open();
        myDocx.addBlankLine(myBefore, myDocx.getRenderingOptions().DEFAULT_STYLE);
    }

    @Override
    public void close() {
        myDocx.addBlankLine(myAfter, myDocx.getRenderingOptions().DEFAULT_STYLE);
        super.close();
    }

    @Override
    public void getPPr(PPr pPr) {
        myDocx.getHelper().ensureSpacing(pPr);
        PPrBase.Spacing spacing = pPr.getSpacing();
        spacing.setBefore(BigInteger.ZERO);
        spacing.setAfter(BigInteger.ZERO);
        super.getPPr(pPr);
    }
}
