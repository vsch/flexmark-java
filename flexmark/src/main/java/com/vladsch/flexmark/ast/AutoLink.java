package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class AutoLink extends DelimitedLinkNode {
    public AutoLink() {
    }

    public AutoLink(BasedSequence chars) {
        super(chars);
    }

    public AutoLink(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
