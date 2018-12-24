package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

public final class Text extends Node {
    public Text() {
    }

    public Text(BasedSequence chars) {
        super(chars);
    }

    public Text(String chars) {
        super(CharSubSequence.of(chars));
    }

    public Text(String chars, BasedSequence baseSeq) {
        super(PrefixedSubSequence.of(chars, baseSeq));
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
        if (getChars() instanceof PrefixedSubSequence) {
            astChars(out, getChars(), "text");
        }
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
