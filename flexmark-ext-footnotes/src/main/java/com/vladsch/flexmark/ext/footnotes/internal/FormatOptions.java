package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

public class FormatOptions {

    public final ElementPlacement footnotePlacement;
    public final ElementPlacementSort footnoteSort;

    public FormatOptions(DataHolder options) {
        footnotePlacement = FootnoteExtension.FOOTNOTE_PLACEMENT.getFrom(options);
        footnoteSort = FootnoteExtension.FOOTNOTE_SORT.getFrom(options);
    }
}
