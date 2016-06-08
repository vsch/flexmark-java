package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

public class Code extends DelimitedNodeImpl {
    public Code() {
    }

    public Code(BasedSequence chars) {
        super(chars);
    }

    public Code(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }

    @Override
    public String getAstExtra() {
        return delimitedSegmentSpan(openingMarker, text, closingMarker, "text");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
