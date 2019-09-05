package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;

public class FootnoteBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    public FootnoteBlockFormatProvider(DocxContext<T> docx) {
        super(docx, docx.getRenderingOptions().FOOTNOTE_STYLE);
    }

    @Override
    protected void inheritParentFormat(PPr pPr, PPr parentPPr) {
        // do not inherit otherwise the formatting for the footnote reference is
        // applied to footnote block children
    }

    @Override
    protected BlockFormatProvider<T> getStyleParent() {
        return null;
    }
}
