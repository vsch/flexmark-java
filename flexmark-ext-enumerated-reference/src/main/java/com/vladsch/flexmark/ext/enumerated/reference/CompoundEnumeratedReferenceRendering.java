package com.vladsch.flexmark.ext.enumerated.reference;

public class CompoundEnumeratedReferenceRendering {
    final public int ordinal;
    final public EnumeratedReferenceBlock referenceFormat;
    final public String defaultText;
    final public boolean needSeparator;

    public CompoundEnumeratedReferenceRendering(final int ordinal, final EnumeratedReferenceBlock referenceFormat, final String defaultText, final boolean needSeparator) {
        this.ordinal = ordinal;
        this.referenceFormat = referenceFormat;
        this.defaultText = defaultText;
        this.needSeparator = needSeparator;
    }
}
