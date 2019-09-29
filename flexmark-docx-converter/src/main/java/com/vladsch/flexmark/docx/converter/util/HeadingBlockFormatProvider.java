package com.vladsch.flexmark.docx.converter.util;

public class HeadingBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    private final DocxContext<T> myDocx;
    private final int myHeadingLevel;

    public HeadingBlockFormatProvider(DocxContext<T> docx, int headingLevel) {
        super(docx, docx.getRenderingOptions().HEADINGS[headingLevel]);
        myDocx = docx;
        myHeadingLevel = headingLevel;
    }
}
