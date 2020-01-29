package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;

@SuppressWarnings("WeakerAccess")
public class AbbreviationFormatOptions {
    final public ElementPlacement abbreviationsPlacement;
    final public ElementPlacementSort abbreviationsSort;

    public AbbreviationFormatOptions(DataHolder options) {
        abbreviationsPlacement = AbbreviationExtension.ABBREVIATIONS_PLACEMENT.get(options);
        abbreviationsSort = AbbreviationExtension.ABBREVIATIONS_SORT.get(options);
    }
}
