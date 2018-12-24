package com.vladsch.flexmark.parser.core.delimiter;

public class AsteriskDelimiterProcessor extends EmphasisDelimiterProcessor {

    public AsteriskDelimiterProcessor(boolean strongWrapsEmphasis) {
        super('*', strongWrapsEmphasis);
    }
}
