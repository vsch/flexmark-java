package com.vladsch.flexmark.docx.converter;

import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.Style;

public interface BlockFormatProvider extends ParaContainer {
    /*

    Provides containment of other format blocks to allow
    inheritance of formatting from parent to child

    Specifically, indentation is combined with that of the parent so that
    child elements are located within the parent's client area.

    Where possible, border is passed into the child but offset to the original
    border provider's location. That way block quote borders are propagated
    to children even if they are indented. However the limit for Word is border
    being 31pt offset from text so not much indenting can be done before the border
    shifts.

    Effectively, the style settings are made explicit if their combination
    differs from the style values.

    Handle containment of P elements in parent, for tables these are
    put into the cell for all others they are added to the document.

    */

    // these are extracted and combined using format merge by each block formatter
    // using the docx context for helping with the mash-up of formatting
    // PPrBase.Ind getInd();
    // PPrBase.Spacing getSpacing();
    // PPrBase.NumPr getNumPr();
    // PPrBase.PBdr getPBdr();
    // PShading getPShading();

    // initialize internal stuff based on the parent for future use
    // optionally add elements to doc before main elements
    // any P creates will call the getPPr(), getParaRPr() and addP() for this provider
    // so don't create elements until you are ready to handle these calls
    void open();

    // finalize, add elements to the document after main part as needed
    // after this method returns no more calls will be made to this provider
    void close();

    // these are the hard-coded styles on which this block is based
    // used for reference purposes by children to figure out what they need to inherit
    Style getBaseStyle();

    // get the final PPr for the next P of this provider
    PPrBase getPPr();

    // get the final ParaRPr for the next P of this provider
    ParaRPr getParaRPr();
}
