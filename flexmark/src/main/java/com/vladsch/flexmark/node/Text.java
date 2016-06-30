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
        if (getChars().length() <= 10) {
            segmentSpanChars(out, getChars(), "chars");
        } else {
            // give the first 5 and last 5
            segmentSpanChars(out, getChars().getStartOffset(), getChars().getEndOffset(), "chars", getChars().subSequence(0, 5).toVisibleWhitespaceString() + "\"...\"" + getChars().subSequence(getChars().length() - 5).toVisibleWhitespaceString());
        }
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
