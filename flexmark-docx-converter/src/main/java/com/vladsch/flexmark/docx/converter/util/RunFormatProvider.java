package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public interface RunFormatProvider<T> extends FormatProvider<T> {
    // for space preservation
    String SPACE_PRESERVE = "preserve";

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

    // set the final PPr for the next P of this provider, that is different from what the style provides
    void getRPr(RPr rPr);

    RunFormatProvider<T> getRunParent();
}
