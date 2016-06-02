package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class Code extends DelimitedNode {
    public Code() {
    }

    public Code(BasedSequence chars) {
        super(chars);
    }

    public Code(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
