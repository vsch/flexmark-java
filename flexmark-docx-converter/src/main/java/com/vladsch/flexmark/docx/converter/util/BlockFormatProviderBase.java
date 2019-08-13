package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.model.styles.StyleUtil;
import org.docx4j.wml.*;

/*
    Base Implementation for all BlockFormatProviders
 */
@SuppressWarnings("CallToSimpleGetterFromWithinClass")
public class BlockFormatProviderBase<T> implements BlockFormatProvider<T> {
    protected final DocxContext<T> myDocx;
    protected final T myFrame;
    protected final BlockFormatProvider<T> myParent;
    protected final String myBaseStyleId;
    protected int myPCount;

    public BlockFormatProviderBase(DocxContext<T> docx, String baseStyleId) {
        myDocx = docx;
        myFrame = docx.getContextFrame();
        myParent = docx.getBlockFormatProvider();
        myBaseStyleId = baseStyleId;
        myPCount = 0;
    }

    @Override
    public void adjustPPrForFormatting(PPr pP) {
        myPCount++;
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

    protected Style getBaseStyle() {
        return myDocx.getStyle(getBaseStyleId());
    }

    @Override
    public Style getStyle() {
        return myDocx.getStyle(getStyleId());
    }

    protected String getBaseStyleId() {
        return myBaseStyleId;
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
    protected BlockFormatProvider<T> getStyleParent() {
        return myParent;
    }

    @Override
    public BlockFormatProvider<T> getBlockParent() {
        return myParent;
    }

    protected void inheritIndent(PPr pPrBase, PPr parentPrBase) {
        if (parentPrBase != null) {
            myDocx.getHelper().inheritInd(pPrBase, parentPrBase);
        }
    }

    protected void inheritParentFormat(PPr pPr, PPr parentPPr) {
        inheritIndent(pPr, parentPPr);
        inheritBdr(pPr, parentPPr);
    }

    protected void adjustPPr(PPr pPrBase) {

    }

    /**
     * Inherit left border
     * <p>
     * must be called after ind has been determined
     *
     * @param pPr       ppr to set
     * @param parentPPr parent ppr
     */
    protected void inheritBdr(PPr pPr, PPr parentPPr) {
        // combine indent with parent
        myDocx.getHelper().inheritPBdr(pPr, parentPPr);
    }

    @Override
    public void getPPr(PPr pPr) {
        // Create object for pStyle if one does not already exist
        if (myBaseStyleId != null) {
            PPrBase.PStyle basePStyle = myDocx.getFactory().createPPrBasePStyle();
            pPr.setPStyle(basePStyle);
            basePStyle.setVal(myBaseStyleId);
        }

        // Create object for rPr
        ParaRPr pararpr = pPr.getRPr();
        if (pararpr == null) {
            pararpr = myDocx.getFactory().createParaRPr();
            pPr.setRPr(pararpr);
        }

        // handle inheritance
        BlockFormatProvider<T> parent = getStyleParent();
        if (parent != null) {
            PPr ppr = myDocx.getFactory().createPPr();
            parent.getPPr(ppr);
            ppr = myDocx.getHelper().getExplicitPPr(ppr);

            //PPr ppr = myDocx.getFactory().createPPr();
            //Style parentStyle = myDocx.getStyle(parent.getStyleId());
            //if (parentStyle != null) {
            //    myDocx.getHelper().setPPrBase(ppr, parentStyle.getPPr(), false);
            //}
            //parent.getPPr(ppr);

            inheritParentFormat(pPr, ppr);
        }

        // allow adjustments
        adjustPPr(pPr);
    }

    @Override
    public void getParaRPr(RPr rPr) {
        BlockFormatProvider<T> parent = getStyleParent();
        if (parent != null) {
            parent.getParaRPr(rPr);
        }

        Style style = getStyle();
        if (style != null && style.getRPr() != null) {
            StyleUtil.apply(myDocx.getHelper().getExplicitRPr(style.getRPr()), rPr);
        }
    }
}
