package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.data.DataHolder;

@SuppressWarnings("WeakerAccess")
public class FormatOptions {
    public final ElementPlacement abbreviationsPlacement;
    public final ElementPlacementSort abbreviationsSort;

    public FormatOptions(DataHolder options) {
        abbreviationsPlacement = AbbreviationExtension.ABBREVIATIONS_PLACEMENT.getFrom(options);
        abbreviationsSort = AbbreviationExtension.ABBREVIATIONS_SORT.getFrom(options);
    }
}
