package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.DocxRendererContext;
import com.vladsch.flexmark.docx.converter.RunFormatProvider;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.wml.*;

/*
    Base Implementation for all RunFormatProviders
 */
public class RunFormatProviderBase implements RunFormatProvider {
    protected final DocxRendererContext myDocx;
    protected final Node myNode;
    protected final RunFormatProvider myParent;
    protected final String myBaseStyleId;

    public RunFormatProviderBase(final DocxRendererContext docx, final String baseStyleId) {
        myDocx = docx;
        myNode = docx.getCurrentNode();
        myParent = docx.getRunFormatProvider();
        myBaseStyleId = baseStyleId;
    }

    @Override
    public Node getNode() {
        return myNode;
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

    /**
     * Get the style parent for the next P of this block
     *
     * @return parent to use for style inheritance
     */
    protected RunFormatProvider getStyleParent() {
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
        RStyle rstyle = myDocx.getFactory().createRStyle();
        rPr.setRStyle(rstyle);
        rstyle.setVal(myBaseStyleId);

        // handle inheritance
        RunFormatProvider parent = myParent;
        if (parent != null) {
            RPr rpr1 = myDocx.getFactory().createRPr();
            parent.getRPr(rpr1);

            inheritParentStyle(rPr, rpr1);
        }

        //Style thisStyle = myDocx.getStyle(myBaseStyleId);
        //if (thisStyle != null) {
        //    final RPr pr = myDocx.getHelper().getExplicitRPr(thisStyle.getRPr());
        //    final ParaRPr paraRPr = myDocx.getP().getPPr().getRPr();
        //    myDocx.getHelper().keepDiff(rPr, pr);
        //    myDocx.getHelper().keepDiff(rPr, paraRPr);
        //}
    }
}
