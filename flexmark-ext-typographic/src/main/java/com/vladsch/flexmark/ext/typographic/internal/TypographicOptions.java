package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.util.data.DataHolder;

public class TypographicOptions {
    final public boolean typographicQuotes;
    final public boolean typographicSmarts;
    final public String ellipsis;
    final public String ellipsisSpaced;
    final public String enDash;
    final public String emDash;
    final public String singleQuoteOpen;
    final public String singleQuoteClose;
    final public String singleQuoteUnmatched;
    final public String doubleQuoteOpen;
    final public String doubleQuoteClose;
    final public String doubleQuoteUnmatched;
    final public String angleQuoteOpen;
    final public String angleQuoteClose;
    final public String angleQuoteUnmatched;

    public TypographicOptions(DataHolder options) {
        this.typographicQuotes = TypographicExtension.ENABLE_QUOTES.get(options);
        this.typographicSmarts = TypographicExtension.ENABLE_SMARTS.get(options);
        this.ellipsis = TypographicExtension.ELLIPSIS.get(options);
        this.ellipsisSpaced = TypographicExtension.ELLIPSIS_SPACED.get(options);
        this.enDash = TypographicExtension.EN_DASH.get(options);
        this.emDash = TypographicExtension.EM_DASH.get(options);
        this.singleQuoteOpen = TypographicExtension.SINGLE_QUOTE_OPEN.get(options);
        this.singleQuoteClose = TypographicExtension.SINGLE_QUOTE_CLOSE.get(options);
        this.singleQuoteUnmatched = TypographicExtension.SINGLE_QUOTE_UNMATCHED.get(options);
        this.doubleQuoteOpen = TypographicExtension.DOUBLE_QUOTE_OPEN.get(options);
        this.doubleQuoteClose = TypographicExtension.DOUBLE_QUOTE_CLOSE.get(options);
        this.doubleQuoteUnmatched = TypographicExtension.DOUBLE_QUOTE_UNMATCHED.get(options);
        this.angleQuoteOpen = TypographicExtension.ANGLE_QUOTE_OPEN.get(options);
        this.angleQuoteClose = TypographicExtension.ANGLE_QUOTE_CLOSE.get(options);
        this.angleQuoteUnmatched = TypographicExtension.ANGLE_QUOTE_UNMATCHED.get(options);
    }
}
