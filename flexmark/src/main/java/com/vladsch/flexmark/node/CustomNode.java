package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

public abstract class CustomNode extends Node {
    public CustomNode() {
    }

    public CustomNode(BasedSequence chars) {
        super(chars);
    }
}
