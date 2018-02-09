package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;

public class FootnoteBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    public FootnoteBlockFormatProvider(final DocxContext<T> docx) {
        super(docx, docx.getRenderingOptions().FOOTNOTE_STYLE);
    }

    @Override
    protected void inheritParentFormat(final PPr pPr, final PPr parentPPr) {
        // do not inherit otherwise the formatting for the footnote reference is
        // applied to footnote block children
        int tmp = 0;
    }

    @Override
    protected BlockFormatProvider<T> getStyleParent() {
        return null;
    }
}
