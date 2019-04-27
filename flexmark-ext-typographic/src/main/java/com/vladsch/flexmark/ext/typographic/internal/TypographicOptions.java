package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.util.data.DataHolder;

public class TypographicOptions {
    public final boolean typographicQuotes;
    public final boolean typographicSmarts;
    public final String ellipsis;
    public final String ellipsisSpaced;
    public final String enDash;
    public final String emDash;
    public final String singleQuoteOpen;
    public final String singleQuoteClose;
    public final String singleQuoteUnmatched;
    public final String doubleQuoteOpen;
    public final String doubleQuoteClose;
    public final String doubleQuoteUnmatched;
    public final String angleQuoteOpen;
    public final String angleQuoteClose;
    public final String angleQuoteUnmatched;

    public TypographicOptions(DataHolder options) {
        this.typographicQuotes = TypographicExtension.ENABLE_QUOTES.getFrom(options);
        this.typographicSmarts = TypographicExtension.ENABLE_SMARTS.getFrom(options);
        this.ellipsis = TypographicExtension.ELLIPSIS.getFrom(options);
        this.ellipsisSpaced = TypographicExtension.ELLIPSIS_SPACED.getFrom(options);
        this.enDash = TypographicExtension.EN_DASH.getFrom(options);
        this.emDash = TypographicExtension.EM_DASH.getFrom(options);
        this.singleQuoteOpen = TypographicExtension.SINGLE_QUOTE_OPEN.getFrom(options);
        this.singleQuoteClose = TypographicExtension.SINGLE_QUOTE_CLOSE.getFrom(options);
        this.singleQuoteUnmatched = TypographicExtension.SINGLE_QUOTE_UNMATCHED.getFrom(options);
        this.doubleQuoteOpen = TypographicExtension.DOUBLE_QUOTE_OPEN.getFrom(options);
        this.doubleQuoteClose = TypographicExtension.DOUBLE_QUOTE_CLOSE.getFrom(options);
        this.doubleQuoteUnmatched = TypographicExtension.DOUBLE_QUOTE_UNMATCHED.getFrom(options);
        this.angleQuoteOpen = TypographicExtension.ANGLE_QUOTE_OPEN.getFrom(options);
        this.angleQuoteClose = TypographicExtension.ANGLE_QUOTE_CLOSE.getFrom(options);
        this.angleQuoteUnmatched = TypographicExtension.ANGLE_QUOTE_UNMATCHED.getFrom(options);
    }
}
