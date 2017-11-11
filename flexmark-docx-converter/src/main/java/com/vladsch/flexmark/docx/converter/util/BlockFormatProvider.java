package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.PPr;
import org.docx4j.wml.RPr;

public interface BlockFormatProvider<T> extends FormatProvider<T> {
    String DEFAULT_STYLE = "Normal";
    String LOOSE_PARAGRAPH_STYLE = "ParagraphTextBody";
    String TIGHT_PARAGRAPH_STYLE = "TextBody";
    String PREFORMATTED_TEXT_STYLE = "PreformattedText";
    String BLOCK_QUOTE_STYLE = "Quotations";
    String HORIZONTAL_LINE_STYLE = "HorizontalLine";
    String TABLE_CAPTION = "TableCaption";
    String TABLE_CONTENTS = "TableContents";
    String TABLE_HEADING = "TableHeading";
    String FOOTNOTE_STYLE = "Footnote";

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

    // get the final PPr for the next P of this provider
    void getPPr(final PPr pPr);

    // get the final ParaRPr for the next P of this provider
    void getParaRPr(final RPr rPr);

    // to allow formatter to track first P formatting
    void adjustPPrForFormatting(final PPr pP);

    BlockFormatProvider<T> getBlockParent();
}
