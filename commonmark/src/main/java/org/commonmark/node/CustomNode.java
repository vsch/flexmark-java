package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public abstract class CustomNode extends Node {
    public CustomNode() {
    }

    public CustomNode(BasedSequence chars) {
        super(chars);
    }
}
