package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class BlankLine extends Block {
    public BlankLine() {
    }

    public BlankLine(BasedSequence chars) {
        super(chars);
        setCharsFromContent();
    }

    @Override
    public BasedSequence[] getSegments() {
        return Node.EMPTY_SEGMENTS;
    }
}
