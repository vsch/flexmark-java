package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class CustomNode extends Node {
    public CustomNode() {
    }

    public CustomNode(BasedSequence chars) {
        super(chars);
    }
}
