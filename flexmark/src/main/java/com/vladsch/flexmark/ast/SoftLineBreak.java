package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotAttributeDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class SoftLineBreak extends Node implements DoNotAttributeDecorate {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public SoftLineBreak() {
    }

    public SoftLineBreak(BasedSequence chars) {
        super(chars);
    }
}
