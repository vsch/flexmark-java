package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.docx.converter.BlockFormatProvider;
import org.docx4j.model.properties.paragraph.PShading;
import org.docx4j.wml.*;
import org.w3c.dom.css.CSSPrimitiveValue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

/*
    Base Implementation for all BlockFormatProviders
 */
public abstract class BlockFormatProviderBase implements BlockFormatProvider {
    public static final String LOOSE_PARAGRAPH_STYLE = "ParagraphTextBody";
    public static final String TIGHT_PARAGRAPH_STYLE = "TextBody";
    public static final String PREFORMATTED_TEXT_STYLE = "PreformattedText";
    public static final String BLOCK_QUOTE_STYLE = "Quotations";
    public static final String HORIZONTAL_LINE_STYLE = "HorizontalLine";

    protected final DocxRendererContext myDocx;
    protected final BlockFormatProvider myParent;
    protected final String myBaseStyleId;
    protected final PPrBase myParentPPr;
    protected final ParaRPr myParentParaRPr;

    public BlockFormatProviderBase(final DocxRendererContext docx, final BlockFormatProvider parent, final String baseStyleId) {
        myDocx = docx;
        myParent = parent;
        myBaseStyleId = baseStyleId;
        myParentPPr = getPPr(false);
        myParentParaRPr = getParaRPr(false);
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public Style getBaseStyle() {
        return myDocx.getStyle(myBaseStyleId);
    }

    final protected PPr getPPr(final boolean whenNull) {
        PPr pPr = myDocx.getObjectFactory().createPPr();
        myDocx.getDocxHelper().setPPrBase(pPr, myParentPPr, whenNull);
        return pPr;
    }

    protected ParaRPr getParaRPr(final boolean whenNull) {
        return myDocx.getDocxHelper().getCopy(myParentParaRPr, whenNull);
    }

    @Override
    public PPr getPPr() {
        return getPPr(false);
    }

    @Override
    public ParaRPr getParaRPr() {
        return getParaRPr(false);
    }

    @Override
    public void addP(final P p) {
        myParent.addP(p);
    }
}
