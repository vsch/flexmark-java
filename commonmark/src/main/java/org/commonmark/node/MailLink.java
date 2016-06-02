package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class MailLink extends Node {
    public MailLink() {
    }

    public MailLink(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
