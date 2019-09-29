package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public class FootnoteRunFormatProvider<T> extends RunFormatProviderBase<T> {
    public FootnoteRunFormatProvider(DocxContext<T> docx) {
        super(docx, docx.getRenderingOptions().FOOTNOTE_TEXT, false, "");
    }

    @Override
    public void getRPr(RPr rPr) {

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
    protected void inheritParentStyle(RPr rPr, RPr parentRPr) {

    }
}
