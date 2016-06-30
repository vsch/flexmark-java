package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

public class DummyNode extends Node {
    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
