package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Code extends DelimitedNodeImpl implements DoNotLinkDecorate {
    public Code() {
    }

    public Code(BasedSequence chars) {
        super(chars);
    }

    public Code(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpan(out, openingMarker, text, closingMarker, "text");
    }
}
