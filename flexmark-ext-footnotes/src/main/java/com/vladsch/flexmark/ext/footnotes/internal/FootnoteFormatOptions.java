package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;

public class FootnoteFormatOptions {

    final public ElementPlacement footnotePlacement;
    final public ElementPlacementSort footnoteSort;

    public FootnoteFormatOptions(DataHolder options) {
        footnotePlacement = FootnoteExtension.FOOTNOTE_PLACEMENT.get(options);
        footnoteSort = FootnoteExtension.FOOTNOTE_SORT.get(options);
    }
}
