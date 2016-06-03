package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.StringSequence;

public class Text extends Node {
    public Text() {
    }

    public Text(BasedSequence chars) {
        super(chars);
    }

    public Text(String chars) {
        super(new StringSequence(chars));
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + chars;
    }
}
