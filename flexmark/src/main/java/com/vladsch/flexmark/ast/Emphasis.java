package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Emphasis extends DelimitedNodeImpl {
    public Emphasis() {
    }

    public Emphasis(BasedSequence chars) {
        super(chars);
    }

    public Emphasis(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }
}
