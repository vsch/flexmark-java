package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class StrongEmphasis extends DelimitedNode {
    public StrongEmphasis() {
    }

    public StrongEmphasis(BasedSequence chars) {
        super(chars);
    }

    public StrongEmphasis(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
