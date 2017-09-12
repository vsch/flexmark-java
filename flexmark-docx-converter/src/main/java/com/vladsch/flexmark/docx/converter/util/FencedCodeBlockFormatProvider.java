package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.Style;

import java.math.BigInteger;

public class FencedCodeBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    private final DocxContext<T> myDocx;
    private final BigInteger myBefore;
    private final BigInteger myAfter;

    public FencedCodeBlockFormatProvider(final DocxContext<T> docx) {
        super(docx, BlockFormatProvider.PREFORMATTED_TEXT_STYLE);
        final BigInteger before;
        final BigInteger after;

        final Style paragraphStyle = docx.getStyle(PREFORMATTED_TEXT_STYLE);
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
        myDocx.addBlankLine(myBefore, BlockFormatProvider.DEFAULT_STYLE);
    }

    @Override
    public void close() {
        myDocx.addBlankLine(myAfter, BlockFormatProvider.DEFAULT_STYLE);
        super.close();
    }

    @Override
    public void getPPr(final PPr pPr) {
        myDocx.getHelper().ensureSpacing(pPr);
        final PPrBase.Spacing spacing = pPr.getSpacing();
        spacing.setBefore(BigInteger.ZERO);
        spacing.setAfter(BigInteger.ZERO);
        super.getPPr(pPr);
    }
}
