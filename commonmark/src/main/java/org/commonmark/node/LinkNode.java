package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public abstract class LinkNode extends Node {
    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }
}
