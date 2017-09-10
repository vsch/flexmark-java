package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.DocxRendererContext;
import com.vladsch.flexmark.docx.converter.RunFormatProvider;
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
        Style style = getStyle();

        if (style != null && style.getRPr() != null) {
            RPr diff = myDocx.getHelper().getCopy(parentRPr, false);
            myDocx.getHelper().setRPr(diff, rPr, false);
            myDocx.getHelper().keepDiff(diff, myDocx.getHelper().getExplicitRPr(style.getRPr(), false));
            myDocx.getHelper().setRPr(rPr, diff, false);
        } else {
            myDocx.getHelper().setRPr(rPr, parentRPr, false);
        }
    }

    @Override
    public void getRPr(final RPr rPr) {
        // handle inheritance
        RunFormatProvider parent = getStyleParent();
        if (parent != null) {
            RPr rpr1 = myDocx.getFactory().createRPr();
            Style parentStyle = myDocx.getStyle(parent.getStyleId());
            if (parentStyle != null) {
                myDocx.getHelper().setRPr(rpr1, parentStyle.getRPr(), false);
            }

            inheritParentStyle(rPr, rpr1);

            parent.getRPr(rPr);
        }

        // Create object for rStyle
        RStyle rstyle = myDocx.getFactory().createRStyle();
        rPr.setRStyle(rstyle);
        rstyle.setVal(myBaseStyleId);

        Style thisStyle = myDocx.getStyle(myBaseStyleId);
        if (thisStyle != null) {
            myDocx.getHelper().keepDiff(rPr, thisStyle.getRPr());
        }
    }
}
