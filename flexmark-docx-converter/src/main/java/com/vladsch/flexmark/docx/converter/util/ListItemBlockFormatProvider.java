package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.Style;

import java.math.BigInteger;

public class ListItemBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    final private DocxContext<T> myDocx;
    final private String mySpacingStyleId;
    final private long myIdNum;
    final private int myListLevel;
    final private Class[] mySkipContextFrameClasses;

    public ListItemBlockFormatProvider(DocxContext<T> docx, String listStyle, String listSpacingStyle, long idNum, int listLevel, Class... skipContextFrameClasses) {
        super(docx, listStyle);
        mySpacingStyleId = listSpacingStyle;
        myDocx = docx;
        myIdNum = idNum;
        myListLevel = listLevel;
        mySkipContextFrameClasses = skipContextFrameClasses;
    }

    @Override
    public void getPPr(PPr pPr) {
        if (myPCount == 0) {
            // Create object for numPr
            PPrBase.NumPr numPr = myDocx.getFactory().createPPrBaseNumPr();
            pPr.setNumPr(numPr);

            // Create object for numId
            PPrBase.NumPr.NumId numId = myDocx.getFactory().createPPrBaseNumPrNumId();
            numPr.setNumId(numId);
            numId.setVal(BigInteger.valueOf(myIdNum)); //listNumId));

            // Create object for ilvl
            PPrBase.NumPr.Ilvl ilvl = myDocx.getFactory().createPPrBaseNumPrIlvl();
            numPr.setIlvl(ilvl);
            ilvl.setVal(BigInteger.valueOf(myListLevel));
        } else {
            // need to inherit indent from our base style
            NumberingDefinitionsPart ndp = myDocx.getDocxDocument().getNumberingDefinitionsPart();
            PPrBase.Ind ind = ndp.getInd(String.valueOf(myIdNum), String.valueOf(myListLevel));
            if (ind != null) {
                DocxHelper helper = myDocx.getHelper();
                helper.ensureInd(pPr);
                pPr.getInd().setLeft(helper.safeIndLeft(ind));
                pPr.getInd().setHanging(BigInteger.ZERO);
            }
        }

        super.getPPr(pPr);
    }

    @Override
    protected void adjustPPr(PPr pPrBase) {
        if (mySpacingStyleId != null && !mySpacingStyleId.equals(myBaseStyleId)) {
            // get the spacing from spacing style
            Style style = myDocx.getStyle(mySpacingStyleId);

            if (style != null && style.getPPr() != null) {
                PPr pPr = myDocx.getHelper().getExplicitPPr(style.getPPr());
                PPr pPrExplicitBase = myDocx.getHelper().getExplicitPPr(pPrBase);
                PPrBase pPrDiff = myDocx.getHelper().keepDiff(pPr, pPrExplicitBase);

                StyleUtil.apply(pPrDiff, pPrBase);
            }
        }
    }

    private boolean containsClass(Class[] list, Object item) {
        for (Class nodeType : list) {
            if (nodeType.isInstance(item)) return true;
        }
        return false;
    }

    @Override
    protected BlockFormatProvider<T> getStyleParent() {
        BlockFormatProvider<T> parent = myParent;
        while (parent != null && containsClass(mySkipContextFrameClasses, parent.getProviderFrame())) {
            parent = parent.getBlockParent();
        }
        return parent;
    }

    @Override
    protected void inheritBdr(PPr pPr, PPr parentPPr) {
        super.inheritBdr(pPr, parentPPr);
    }
}
