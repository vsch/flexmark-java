package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

public class MacroFormatOptions {

    public final ElementPlacement macrosPlacement;
    public final ElementPlacementSort macrosSort;

    public MacroFormatOptions(DataHolder options) {
        macrosPlacement = MacrosExtension.MACRO_DEFINITIONS_PLACEMENT.getFrom(options);
        macrosSort = MacrosExtension.MACRO_DEFINITIONS_SORT.getFrom(options);
    }
}
