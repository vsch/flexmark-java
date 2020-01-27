package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;

public class MacroFormatOptions {

    final public ElementPlacement macrosPlacement;
    final public ElementPlacementSort macrosSort;

    public MacroFormatOptions(DataHolder options) {
        macrosPlacement = MacrosExtension.MACRO_DEFINITIONS_PLACEMENT.get(options);
        macrosSort = MacrosExtension.MACRO_DEFINITIONS_SORT.get(options);
    }
}
