package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.parser.delimiter.DelimiterRun;

public class AngleQuoteDelimiterProcessor extends QuoteDelimiterProcessorBase {
    public AngleQuoteDelimiterProcessor(final TypographicOptions options) {
        super(options, '<', '>', options.angleQuoteOpen, options.angleQuoteClose, options.angleQuoteUnmatched);
    }

    @Override
    public int getMinLength() {
        return 2;
    }

    @Override
    public boolean canBeOpener(boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return true;
    }

    @Override
    public boolean canBeCloser(boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return true;
    }

    protected boolean isAllowed(CharSequence seq, int index) {
        return true;
    }
}
