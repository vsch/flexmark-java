package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.docx.converter.internal.DocxRenderer;
import org.docx4j.wml.PPr;

public class IsolatingBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    public IsolatingBlockFormatProvider(final DocxContext<T> docx) {
        super(docx, docx.getRenderingOptions().DEFAULT_STYLE);
    }

    @Override
    protected void inheritBdr(final PPr pPr, final PPr parentPPr) {

    }

    @Override
    protected void inheritIndent(final PPr pPrBase, final PPr parentPrBase) {

    }
}
