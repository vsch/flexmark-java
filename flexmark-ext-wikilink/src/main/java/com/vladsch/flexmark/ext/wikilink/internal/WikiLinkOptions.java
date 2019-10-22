package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.util.data.DataHolder;

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
    public final String imagePrefixAbsolute;
    public final String linkFileExtension;
    public final String linkPrefix;
    public final String linkPrefixAbsolute;
    public final String linkReplaceChars;
    public final String linkEscapeChars;

    public WikiLinkOptions(DataHolder options) {
        this.allowInlines = WikiLinkExtension.ALLOW_INLINES.get(options);
        this.allowAnchors = WikiLinkExtension.ALLOW_ANCHORS.get(options);
        this.disableRendering = WikiLinkExtension.DISABLE_RENDERING.get(options);
        this.imageLinks = WikiLinkExtension.IMAGE_LINKS.get(options);
        this.linkFirstSyntax = WikiLinkExtension.LINK_FIRST_SYNTAX.get(options);
        this.allowAnchorEscape = WikiLinkExtension.ALLOW_ANCHOR_ESCAPE.get(options);
        this.allowPipeEscape = WikiLinkExtension.ALLOW_PIPE_ESCAPE.get(options);
        this.imageFileExtension = WikiLinkExtension.IMAGE_FILE_EXTENSION.get(options);
        this.imagePrefix = WikiLinkExtension.IMAGE_PREFIX.get(options);
        this.imagePrefixAbsolute = WikiLinkExtension.IMAGE_PREFIX_ABSOLUTE.get(options);
        this.linkFileExtension = WikiLinkExtension.LINK_FILE_EXTENSION.get(options);
        this.linkPrefix = WikiLinkExtension.LINK_PREFIX.get(options);
        this.linkPrefixAbsolute = WikiLinkExtension.LINK_PREFIX_ABSOLUTE.get(options);
        this.linkEscapeChars = WikiLinkExtension.LINK_ESCAPE_CHARS.get(options);
        this.linkReplaceChars = WikiLinkExtension.LINK_REPLACE_CHARS.get(options);
    }

    public Object getLinkPrefix(boolean absolute) {
        return absolute ? linkPrefixAbsolute : linkPrefix;
    }

    public Object getImagePrefix(boolean absolute) {
        return absolute ? imagePrefixAbsolute : imagePrefix;
    }
}
