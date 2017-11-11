package com.vladsch.flexmark.docx.converter.util;

public class FootnoteBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    public FootnoteBlockFormatProvider(final DocxContext<T> docx) {
        super(docx, FOOTNOTE_STYLE);
    }

    @Override
    protected BlockFormatProvider<T> getStyleParent() {
        return null;
    }
}
