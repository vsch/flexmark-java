package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class WikiImage extends WikiNode {
    public WikiImage(boolean linkIsFirst) {
        super(linkIsFirst);
    }

    public WikiImage(BasedSequence chars, boolean linkIsFirst, boolean canEscapePipe) {
        super(chars, linkIsFirst, false, canEscapePipe, false);
    }
}
