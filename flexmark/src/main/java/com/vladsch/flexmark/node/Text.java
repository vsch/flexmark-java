package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.StringSequence;

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
        return "text=" + getChars();
    }
}
