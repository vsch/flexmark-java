package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

public abstract class LinkNode extends Node implements DoNotLinkify {
    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }
}
