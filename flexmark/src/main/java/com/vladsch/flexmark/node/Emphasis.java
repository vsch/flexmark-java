package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

public class Emphasis extends DelimitedNodeImpl {
    public Emphasis() {
    }

    public Emphasis(BasedSequence chars) {
        super(chars);
    }

    public Emphasis(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
