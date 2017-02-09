package com.vladsch.flexmark.ext.typographic.internal;

public class AngleQuoteDelimiterProcessor extends QuoteDelimiterProcessorBase {
    public AngleQuoteDelimiterProcessor(final TypographicOptions options) {
        super(options, '<', '>', options.angleQuoteOpen, options.angleQuoteClose, options.angleQuoteUnmatched);
    }

    @Override
    public int getMinLength() {
        return 2;
    }
}
