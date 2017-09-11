package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.BlockFormatProvider;
import com.vladsch.flexmark.docx.converter.DocxRendererContext;
import org.docx4j.wml.*;

/*
    Base Implementation for all BlockFormatProviders
 */
public class BlockFormatProviderBase implements BlockFormatProvider {
    protected final DocxRendererContext myDocx;
    protected final Node myNode;
    protected final BlockFormatProvider myParent;
    protected final String myBaseStyleId;
    protected int myPCount;

    public BlockFormatProviderBase(final DocxRendererContext docx, final String baseStyleId) {
        myDocx = docx;
        myNode = docx.getCurrentNode();
        myParent = docx.getBlockFormatProvider();
        myBaseStyleId = baseStyleId;
        myPCount = 0;
    }

    @Override
    public void adjustPPrForFormatting(final PPr pP) {
        myPCount++;
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
    protected BlockFormatProvider getStyleParent() {
        return myParent;
    }

    @Override
    public BlockFormatProvider getParent() {
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
     * @param pPr   ppr to set
     * @param parentPPr parent ppr
     */
    protected void inheritBdr(PPr pPr, PPr parentPPr) {
        // combine indent with parent
        myDocx.getHelper().inheritPBdr(pPr, parentPPr);
    }

    @Override
    public void getPPr(final PPr pPr) {
        // Create object for pStyle if one does not already exist
        PPrBase.PStyle basePStyle = myDocx.getFactory().createPPrBasePStyle();
        pPr.setPStyle(basePStyle);
        basePStyle.setVal(myBaseStyleId);

        // Create object for rPr
        ParaRPr pararpr = pPr.getRPr();
        if (pararpr == null) {
            pararpr = myDocx.getFactory().createParaRPr();
            pPr.setRPr(pararpr);
        }

        // handle inheritance
        BlockFormatProvider parent = getStyleParent();
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
    public void getParaRPr(final ParaRPr rPr) {
        BlockFormatProvider parent = getStyleParent();
        if (parent != null) {
            parent.getParaRPr(rPr);
        }
    }
}
