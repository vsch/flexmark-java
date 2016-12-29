package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.util.options.DataHolder;

public class WikiLinkOptions {
    public final boolean disableRendering;
    public final boolean imageLinks;
    public final boolean linkFirstSyntax;
    public final String imageFileExtension;
    public final String imagePrefix;
    public final String linkFileExtension;
    public final String linkPrefix;

    public WikiLinkOptions(DataHolder options) {
        this.disableRendering = options.get(WikiLinkExtension.DISABLE_RENDERING);
        this.imageFileExtension = options.get(WikiLinkExtension.IMAGE_FILE_EXTENSION);
        this.imageLinks = options.get(WikiLinkExtension.IMAGE_LINKS);
        this.imagePrefix = options.get(WikiLinkExtension.IMAGE_PREFIX);
        this.linkFileExtension = options.get(WikiLinkExtension.LINK_FILE_EXTENSION);
        this.linkFirstSyntax = options.get(WikiLinkExtension.LINK_FIRST_SYNTAX);
        this.linkPrefix = options.get(WikiLinkExtension.LINK_PREFIX);
    }
}
