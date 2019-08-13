package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;

import java.math.BigInteger;

public class ListItemBlockFormatProvider<T> extends BlockFormatProviderBase<T> {
    private final DocxContext<T> myDocx;
    private final long myIdNum;
    private final int myListLevel;
    private final Class[] mySkipContextFrameClasses;

    public ListItemBlockFormatProvider(DocxContext<T> docx, String listStyle, long idNum, int listLevel, Class... skipContextFrameClasses) {
        super(docx, listStyle);
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
