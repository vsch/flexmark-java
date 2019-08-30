package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Style;

import java.math.BigInteger;

public class HeadingBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    private final DocxContext<T> myDocx;
    private final int myHeadingLevel;

    public HeadingBlockFormatProvider(DocxContext<T> docx, int headingLevel) {
        super(docx, docx.getRenderingOptions().HEADINGS[headingLevel]);
        myDocx = docx;
        myHeadingLevel = headingLevel;
    }
}
