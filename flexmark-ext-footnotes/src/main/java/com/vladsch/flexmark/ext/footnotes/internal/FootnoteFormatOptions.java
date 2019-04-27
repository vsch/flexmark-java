package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.data.DataHolder;

public class FootnoteFormatOptions {

    public final ElementPlacement footnotePlacement;
    public final ElementPlacementSort footnoteSort;

    public FootnoteFormatOptions(DataHolder options) {
        footnotePlacement = FootnoteExtension.FOOTNOTE_PLACEMENT.getFrom(options);
        footnoteSort = FootnoteExtension.FOOTNOTE_SORT.getFrom(options);
    }
}
