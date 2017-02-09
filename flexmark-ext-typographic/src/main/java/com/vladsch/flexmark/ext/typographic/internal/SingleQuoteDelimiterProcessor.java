package com.vladsch.flexmark.ext.typographic.internal;

public class SingleQuoteDelimiterProcessor extends QuoteDelimiterProcessorBase {
    public SingleQuoteDelimiterProcessor(final TypographicOptions options) {
        super(options, '\'', '\'', options.singleQuoteOpen, options.singleQuoteClose, options.singleQuoteUnmatched);
    }
}
