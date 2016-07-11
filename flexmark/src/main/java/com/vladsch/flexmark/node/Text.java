package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.StringSequence;

public class Text extends Node {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    public Text() {
    }

    public Text(BasedSequence chars) {
        super(chars);
    }

    public Text(String chars) {
        super(new StringSequence(chars));
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
