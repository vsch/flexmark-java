package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.model.styles.StyleUtil;
import org.docx4j.wml.*;

/*
    Base Implementation for all RunFormatProviders
 */
public class RunFormatProviderBase<T> implements RunFormatProvider<T> {
    protected final DocxContext<T> myDocx;
    protected final T myFrame;
    protected final RunFormatProvider<T> myParent;
    protected final String myBaseStyleId;
    protected final boolean myNoCharacterStyles;
    protected final String myHighlightShadingColor;

    public RunFormatProviderBase(DocxContext<T> docx, String baseStyleId, boolean noCharacterStyles, String highlightShadingColor) {
        myDocx = docx;
        myFrame = docx.getContextFrame();
        myParent = docx.getRunFormatProvider();
        myBaseStyleId = baseStyleId;
        myNoCharacterStyles = noCharacterStyles;
        myHighlightShadingColor = highlightShadingColor == null ? "" : highlightShadingColor;
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
    public void getRPr(RPr rPr) {
        // Create object for rStyle
        if (!myNoCharacterStyles && myHighlightShadingColor.isEmpty() && myBaseStyleId != null) {
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

        if (myNoCharacterStyles || !myHighlightShadingColor.isEmpty()) {
            Style thisStyle = myBaseStyleId == null ? null : myDocx.getStyle(myBaseStyleId);
            if (thisStyle != null) {
                ParaRPr paraRPr = myDocx.getP().getPPr().getRPr();
                if (!myHighlightShadingColor.isEmpty()) {
                    String color = myHighlightShadingColor;

                    CTShd shd = rPr.getShd();
                    if (shd != null) {
                        String shdFill = shd.getFill();
                        if (shdFill != null && !shdFill.isEmpty() && !shdFill.equals("auto") && color.equals("shade")) {
                            if (ColorNameMapper.isNamedColor(shdFill) || ColorNameMapper.isHexColor(shdFill)) {
                                color = shdFill;
                            }
                        }
                        rPr.setShd(null);
                    }

                    if (ColorNameMapper.isNamedColor(color)) {
                        Highlight highlight = myDocx.getFactory().createHighlight();
                        highlight.setVal(color);
                        rPr.setHighlight(highlight);
                    } else if (ColorNameMapper.isHexColor(color)) {
                        Highlight highlight = myDocx.getFactory().createHighlight();
                        highlight.setVal(ColorNameMapper.findClosestNamedColor(color));
                        rPr.setHighlight(highlight);
                    } else {
                        // not valid color
                    }
                }
                //myDocx.getHelper().keepDiff(rPr, pr);
                myDocx.getHelper().keepDiff(rPr, paraRPr);
            }
        }
    }
}
