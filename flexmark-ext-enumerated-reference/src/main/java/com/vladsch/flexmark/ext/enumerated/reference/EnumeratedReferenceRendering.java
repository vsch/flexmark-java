package com.vladsch.flexmark.ext.enumerated.reference;

public class EnumeratedReferenceRendering {
    public final EnumeratedReferenceBlock referenceFormat;
    public final String referenceType;
    public final int referenceOrdinal;

    public EnumeratedReferenceRendering(EnumeratedReferenceBlock referenceFormat, String referenceType, int referenceOrdinal) {
        this.referenceFormat = referenceFormat;
        this.referenceType = referenceType;
        this.referenceOrdinal = referenceOrdinal;
    }
}
