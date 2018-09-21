package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class FootnoteRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public FootnoteRunFormatProvider(final DocxContext<T> docx) {
        super(docx,docx.getRenderingOptions().FOOTNOTE_TEXT, false,"");
    }

    @Override
    public void getRPr(final RPr rPr) {

    }

    @Override
    public RunFormatProvider<T> getRunParent() {
        return null;
    }

    @Override
    protected RunFormatProvider<T> getStyleParent() {
        return null;
    }

    @Override
    protected void inheritParentStyle(final RPr rPr, final RPr parentRPr) {
        int tmp = 0;
    }
}
