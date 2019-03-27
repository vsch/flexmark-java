package com.vladsch.flexmark.ext.enumerated.reference;

public class EnumeratedReferenceRendering {
    public final EnumeratedReferenceBlock referenceFormat; 
    public final String referenceType;
    public final int referenceOrdinal;

    public EnumeratedReferenceRendering(final EnumeratedReferenceBlock referenceFormat, final String referenceType, final int referenceOrdinal) {
        this.referenceFormat = referenceFormat;
        this.referenceType = referenceType;
        this.referenceOrdinal = referenceOrdinal;
    }
}
