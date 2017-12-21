package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

public class EnumeratedReferenceFormatOptions {

    public final ElementPlacement enumeratedReferencePlacement;
    public final ElementPlacementSort enumeratedReferenceSort;

    public EnumeratedReferenceFormatOptions(DataHolder options) {
        enumeratedReferencePlacement = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT.getFrom(options);
        enumeratedReferenceSort = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_SORT.getFrom(options);
    }
}
