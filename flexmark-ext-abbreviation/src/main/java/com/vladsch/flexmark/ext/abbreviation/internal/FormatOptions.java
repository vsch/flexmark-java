package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.formatter.options.ElementPlacement;
import com.vladsch.flexmark.formatter.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

public class FormatOptions {
    public static final DataKey<ElementPlacement> ABBREVIATIONS_PLACEMENT = new DataKey<>("ABBREVIATIONS_PLACEMENT", ElementPlacement.AS_IS);
    public static final DataKey<ElementPlacementSort> ABBREVIATIONS_SORT = new DataKey<>("ABBREVIATIONS_SORT", ElementPlacementSort.AS_IS);

    public final ElementPlacement abbreviationsPlacement;
    public final ElementPlacementSort abbreviationsSort;

    public FormatOptions(DataHolder options) {
        abbreviationsPlacement = ABBREVIATIONS_PLACEMENT.getFrom(options);
        abbreviationsSort = ABBREVIATIONS_SORT.getFrom(options);
    }
}
