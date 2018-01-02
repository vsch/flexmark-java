package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.util.options.DataHolder;

public class WikiLinkOptions {
    public final boolean allowInlines;
    public final boolean allowAnchors;
    public final boolean disableRendering;
    public final boolean imageLinks;
    public final boolean linkFirstSyntax;
    public final boolean allowAnchorEscape;
    public final boolean allowPipeEscape;
    public final String imageFileExtension;
    public final String imagePrefix;
    public final String linkFileExtension;
    public final String linkPrefix;
	public final String linkReplaceChars;
	public final String linkEscapeChars;

    public WikiLinkOptions(DataHolder options) {
        this.allowInlines = WikiLinkExtension.ALLOW_INLINES.getFrom(options);
        this.allowAnchors = WikiLinkExtension.ALLOW_ANCHORS.getFrom(options);
        this.disableRendering = WikiLinkExtension.DISABLE_RENDERING.getFrom(options);
        this.imageLinks = WikiLinkExtension.IMAGE_LINKS.getFrom(options);
        this.linkFirstSyntax = WikiLinkExtension.LINK_FIRST_SYNTAX.getFrom(options);
        this.allowAnchorEscape = WikiLinkExtension.ALLOW_ANCHOR_ESCAPE.getFrom(options);
        this.allowPipeEscape = WikiLinkExtension.ALLOW_PIPE_ESCAPE.getFrom(options);
        this.imageFileExtension = WikiLinkExtension.IMAGE_FILE_EXTENSION.getFrom(options);
        this.imagePrefix = WikiLinkExtension.IMAGE_PREFIX.getFrom(options);
        this.linkFileExtension = WikiLinkExtension.LINK_FILE_EXTENSION.getFrom(options);
        this.linkPrefix = WikiLinkExtension.LINK_PREFIX.getFrom(options);
        this.linkEscapeChars = WikiLinkExtension.LINK_ESCAPE_CHARS.getFrom(options);
        this.linkReplaceChars = WikiLinkExtension.LINK_REPLACE_CHARS.getFrom(options);
    }
}
