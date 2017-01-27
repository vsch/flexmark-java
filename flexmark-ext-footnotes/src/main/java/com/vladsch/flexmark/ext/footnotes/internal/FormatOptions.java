package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.formatter.options.ElementPlacement;
import com.vladsch.flexmark.formatter.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

public class FormatOptions {
    public static final DataKey<ElementPlacement> FOOTNOTE_PLACEMENT = new DataKey<>("FOOTNOTE_PLACEMENT", ElementPlacement.AS_IS);
    public static final DataKey<ElementPlacementSort> FOOTNOTE_SORT = new DataKey<>("FOOTNOTE_SORT", ElementPlacementSort.AS_IS);

    public final ElementPlacement footnotePlacement;
    public final ElementPlacementSort footnoteSort;

    public FormatOptions(DataHolder options) {
        footnotePlacement = FOOTNOTE_PLACEMENT.getFrom(options);
        footnoteSort = FOOTNOTE_SORT.getFrom(options);
    }
}
