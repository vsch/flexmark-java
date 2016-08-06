package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class LinkNode extends Node implements DoNotDecorate {
    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }
}
