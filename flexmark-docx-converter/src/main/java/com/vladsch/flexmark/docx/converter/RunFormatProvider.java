package com.vladsch.flexmark.docx.converter;

import org.docx4j.wml.RPr;

public interface RunFormatProvider extends FormatProvider {
    public static final String BOLD_STYLE = "StrongEmphasis";
    public static final String ITALIC_STYLE = "Emphasis";
    public static final String STRIKE_THROUGH_STYLE = "Strikethrough";
    public static final String SUBSCRIPT_STYLE = "Superscript";
    public static final String SUPERSCRIPT_STYLE = "Subscript";
    public static final String INS_STYLE = "Underlined";
    public static final String INLINE_CODE_STYLE = "SourceText";
    public static final String HYPERLINK_STYLE = "Hyperlink";

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
}
