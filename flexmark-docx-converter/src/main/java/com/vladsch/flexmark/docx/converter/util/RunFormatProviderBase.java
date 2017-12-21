package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.model.styles.StyleUtil;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.RPr;
import org.docx4j.wml.RStyle;
import org.docx4j.wml.Style;

/*
    Base Implementation for all RunFormatProviders
 */
public class RunFormatProviderBase<T> implements RunFormatProvider<T> {
    protected final DocxContext<T> myDocx;
    protected final T myFrame;
    protected final RunFormatProvider<T> myParent;
    protected final String myBaseStyleId;
    protected final boolean myNoCharacterStyles;

    public RunFormatProviderBase(final DocxContext<T> docx, final String baseStyleId, final boolean noCharacterStyles) {
        myDocx = docx;
        myFrame = docx.getContextFrame();
        myParent = docx.getRunFormatProvider();
        myBaseStyleId = baseStyleId;
        myNoCharacterStyles = noCharacterStyles;
    }

    @Override
    public T getProviderFrame() {
        return myFrame;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public Style getStyle() {
        return myDocx.getStyle(myBaseStyleId);
    }

    @Override
    public String getStyleId() {
        return myBaseStyleId;
    }

    @Override
    public RunFormatProvider<T> getRunParent() {
        return myParent;
    }

    /**
     * Get the style parent for the next P of this block
     *
     * @return parent to use for style inheritance
     */
    protected RunFormatProvider<T> getStyleParent() {
        return myParent;
    }

    protected void inheritParentStyle(RPr rPr, RPr parentRPr) {
        RPr parentStyledRPr = myDocx.getHelper().getExplicitRPr(parentRPr);
        StyleUtil.apply(rPr, parentStyledRPr);
        StyleUtil.apply(parentStyledRPr, rPr);

        Style style = getStyle();
        if (style != null) {
            RPr styleRPr = myDocx.getHelper().getExplicitRPr(style.getRPr());
            StyleUtil.apply(rPr, styleRPr);
            StyleUtil.apply(styleRPr, rPr);
        }
    }

    @Override
    public void getRPr(final RPr rPr) {
        // Create object for rStyle
        if (!myNoCharacterStyles) {
            RStyle rstyle = myDocx.getFactory().createRStyle();
            rPr.setRStyle(rstyle);
            rstyle.setVal(myBaseStyleId);
        }

        // handle inheritance
        RunFormatProvider<T> parent = myParent;
        if (parent != null) {
            RPr rpr1 = myDocx.getFactory().createRPr();
            parent.getRPr(rpr1);

            inheritParentStyle(rPr, rpr1);
        }

        if (myNoCharacterStyles) {
            Style thisStyle = myDocx.getStyle(myBaseStyleId);
            if (thisStyle != null) {
                final RPr pr = myDocx.getHelper().getExplicitRPr(thisStyle.getRPr());
                final ParaRPr paraRPr = myDocx.getP().getPPr().getRPr();
                //myDocx.getHelper().keepDiff(rPr, pr);
                myDocx.getHelper().keepDiff(rPr, paraRPr);
            }
        }
    }
}
