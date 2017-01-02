package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class WikiLink extends WikiNode {
    public WikiLink(boolean linkIsFirst) {
        super(linkIsFirst);
    }

    public WikiLink(BasedSequence chars, boolean linkIsFirst, boolean allowAnchors, boolean canEscapePipe, boolean canEscapeAnchor) {
        super(chars, linkIsFirst, allowAnchors, canEscapePipe, canEscapeAnchor);
    }
}
