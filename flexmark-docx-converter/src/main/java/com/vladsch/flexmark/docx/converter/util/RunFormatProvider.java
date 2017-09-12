package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.RPr;

public interface RunFormatProvider<T> extends FormatProvider<T> {
    String BOLD_STYLE = "StrongEmphasis";
    String ITALIC_STYLE = "Emphasis";
    String STRIKE_THROUGH_STYLE = "Strikethrough";
    String SUBSCRIPT_STYLE = "Subscript";
    String SUPERSCRIPT_STYLE = "Superscript";
    String INS_STYLE = "Underlined";
    String INLINE_CODE_STYLE = "SourceText";
    String HYPERLINK_STYLE = "Hyperlink";
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
    void getRPr(final RPr rPr);

    RunFormatProvider<T> getRunParent();
}
