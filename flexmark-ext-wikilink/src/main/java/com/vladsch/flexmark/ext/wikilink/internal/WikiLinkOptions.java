package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.util.data.DataHolder;

public class WikiLinkOptions {
    final public boolean allowInlines;
    final public boolean allowAnchors;
    final public boolean disableRendering;
    final public boolean imageLinks;
    final public boolean linkFirstSyntax;
    final public boolean allowAnchorEscape;
    final public boolean allowPipeEscape;
    final public String imageFileExtension;
    final public String imagePrefix;
    final public String imagePrefixAbsolute;
    final public String linkFileExtension;
    final public String linkPrefix;
    final public String linkPrefixAbsolute;
    final public String linkReplaceChars;
    final public String linkEscapeChars;

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
