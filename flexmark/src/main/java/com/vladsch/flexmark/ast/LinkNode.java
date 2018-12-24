package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class LinkNode extends LinkNodeBase implements DoNotLinkDecorate {

    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }
}
