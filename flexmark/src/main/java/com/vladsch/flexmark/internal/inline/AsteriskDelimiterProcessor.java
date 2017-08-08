package com.vladsch.flexmark.internal.inline;

public class AsteriskDelimiterProcessor extends EmphasisDelimiterProcessor {

    public AsteriskDelimiterProcessor(boolean strongWrapsEmphasis) {
        super('*', strongWrapsEmphasis);
    }
}
