package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

public class AbbreviationOptions {
    protected final boolean useLinks;

    public AbbreviationOptions(DataHolder options) {
        this.useLinks = options.get(AbbreviationExtension.USE_LINKS);
    }
}
