package com.vladsch.flexmark.docx.converter.util;

public class HeadingBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    public HeadingBlockFormatProvider(DocxContext<T> docx, int headingLevel) {
        super(docx, docx.getRenderingOptions().HEADINGS[headingLevel]);
    }
}
