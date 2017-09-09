package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.docx.converter.internal.DocxRendererContext;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Style;

public interface RunFormatProvider {
    /*

    Provides containment of other format runs to allow
    inheritance of formatting from parent to child

    Specifically, font and color information is combined with
    that of the parent so that child elements can set only their
    respective run properties.

    Effectively, the style settings are made explicit if their combination
    differs from the style values.

    Handle containment of R elements in parent, for Hyperlinks these are
    put into the hyperlink element. Others just add to the current P element

    */

    // these are extracted and combined using format merge by each run formatter
    // using the docx context for helping with the mash-up of formatting

    // initialize internal stuff based on the parent and store the docx context for future use
    // optionally add elements to doc before main elements
    // any R creates will call the getRPr() and addR() for this provider
    // so don't create elements until you are ready to handle these calls
    void open();

    // finalize, add elements to the document after main part as needed
    // after this method returns no more calls will be made to this provider
    void close();

    // these are the hard-coded styles on which this block is based
    // used for reference purposes by children to figure out what they need to inherit
    Style getBaseStyle();

    // get the final PPr for the next P of this provider
    RPr getRPr();

    // add a new R and set it's RPr to proper format, action can be different for first r and subsequent ones
    void addR(R r);
}
