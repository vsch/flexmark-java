package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class StrongEmphasis extends DelimitedNodeImpl {
    public StrongEmphasis() {
    }

    public StrongEmphasis(BasedSequence chars) {
        super(chars);
    }

    public StrongEmphasis(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }
}
