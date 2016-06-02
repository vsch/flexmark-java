package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class AutoLink extends Node {
    public AutoLink() {
    }

    public AutoLink(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
