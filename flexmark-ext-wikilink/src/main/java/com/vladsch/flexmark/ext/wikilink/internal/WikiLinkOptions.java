package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.util.options.DataHolder;

public class WikiLinkOptions {
    public final boolean linkFirstSyntax;
    public final boolean disableRendering;
    public final String linkPrefix;
    public final String linkFileExtension;

    public WikiLinkOptions(DataHolder options) {
        this.linkFirstSyntax = options.get(WikiLinkExtension.LINK_FIRST_SYNTAX);
        this.disableRendering = options.get(WikiLinkExtension.DISABLE_RENDERING);
        this.linkPrefix = options.get(WikiLinkExtension.LINK_PREFIX);
        this.linkFileExtension = options.get(WikiLinkExtension.LINK_FILE_EXTENSION);
    }
}
