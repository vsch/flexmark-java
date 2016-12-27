package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class TypographicOptions {
    public final boolean typographicQuotes;
    public final boolean typographicSmarts;

    public TypographicOptions(DataHolder options) {
        this.typographicQuotes = options.get(TypographicExtension.TYPOGRAPHIC_QUOTES);
        this.typographicSmarts = options.get(TypographicExtension.TYPOGRAPHIC_SMARTS);
    }
}
