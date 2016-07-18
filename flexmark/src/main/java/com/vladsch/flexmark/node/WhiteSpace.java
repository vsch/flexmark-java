package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

/**
 * Only generated for CharacterNodeFactory custom parsing
 */
public class WhiteSpace extends Node {
    public WhiteSpace() {
    }

    public WhiteSpace(BasedSequence chars) {
        super(chars);
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
