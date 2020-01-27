package com.vladsch.flexmark.ext.enumerated.reference;

public class EnumeratedReferenceRendering {
    final public EnumeratedReferenceBlock referenceFormat;
    final public String referenceType;
    final public int referenceOrdinal;

    public EnumeratedReferenceRendering(EnumeratedReferenceBlock referenceFormat, String referenceType, int referenceOrdinal) {
        this.referenceFormat = referenceFormat;
        this.referenceType = referenceType;
        this.referenceOrdinal = referenceOrdinal;
    }
}
