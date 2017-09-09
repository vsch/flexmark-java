package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.docx.converter.BlockFormatProvider;
import com.vladsch.flexmark.docx.converter.RunFormatProvider;
import org.docx4j.wml.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

/*
    Base Implementation for all RunFormatProviders
 */
public abstract class RunFormatProviderBase implements RunFormatProvider {
    public static final String BOLD_STYLE = "StrongEmphasis";
    public static final String ITALIC_STYLE = "Emphasis";
    public static final String INLINE_CODE_STYLE = "SourceText";
    public static final String HYPERLINK_STYLE = "Hyperlink";

    protected final DocxRendererContext myDocx;
    protected final RunFormatProvider myParent;
    protected final String myBaseStyleId;
    protected final RPr myParentRPr;

    public RunFormatProviderBase(final DocxRendererContext docx, final RunFormatProvider parent, final String baseStyleId) {
        myDocx = docx;
        myParent = parent;
        myBaseStyleId = baseStyleId;
        myParentRPr = getRPr(false);
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

    final protected RPr getRPr(final boolean whenNull) {
        RPr rPr = myDocx.getObjectFactory().createRPr();
        myDocx.getDocxHelper().setRPr(rPr, myParentRPr, whenNull);
        return rPr;

    }

    @Override
    public RPr getRPr() {
        return getRPr(false);
    }

    @Override
    public void addR(final R r) {
        myParent.addR(r);
    }
}
