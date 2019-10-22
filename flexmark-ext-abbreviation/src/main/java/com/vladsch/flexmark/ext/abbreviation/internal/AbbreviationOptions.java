package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.data.DataHolder;

public class AbbreviationOptions {
    protected final boolean useLinks;

    public AbbreviationOptions(DataHolder options) {
        this.useLinks = AbbreviationExtension.USE_LINKS.get(options);
    }
}
