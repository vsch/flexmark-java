package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;

public class IsolatingBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    public IsolatingBlockFormatProvider(DocxContext<T> docx) {
        super(docx, docx.getRenderingOptions().DEFAULT_STYLE);
    }

    @Override
    protected void inheritBdr(PPr pPr, PPr parentPPr) {

    }

    @Override
    protected void inheritIndent(PPr pPrBase, PPr parentPrBase) {

    }
}
