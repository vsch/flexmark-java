package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.util.options.DataHolder;

public class WikiLinkOptions {
    public final boolean allowInlines;
    public final boolean disableRendering;
    public final boolean imageLinks;
    public final boolean linkFirstSyntax;
    public final String imageFileExtension;
    public final String imagePrefix;
    public final String linkFileExtension;
    public final String linkPrefix;

    public WikiLinkOptions(DataHolder options) {
        this.allowInlines = WikiLinkExtension.ALLOW_INLINES.getFrom(options);
        this.disableRendering = WikiLinkExtension.DISABLE_RENDERING.getFrom(options);
        this.imageFileExtension = WikiLinkExtension.IMAGE_FILE_EXTENSION.getFrom(options);
        this.imageLinks = WikiLinkExtension.IMAGE_LINKS.getFrom(options);
        this.imagePrefix = WikiLinkExtension.IMAGE_PREFIX.getFrom(options);
        this.linkFileExtension = WikiLinkExtension.LINK_FILE_EXTENSION.getFrom(options);
        this.linkFirstSyntax = WikiLinkExtension.LINK_FIRST_SYNTAX.getFrom(options);
        this.linkPrefix = WikiLinkExtension.LINK_PREFIX.getFrom(options);
    }
}
