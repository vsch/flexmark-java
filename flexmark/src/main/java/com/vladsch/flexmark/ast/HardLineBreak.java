package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class HardLineBreak extends Node {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public HardLineBreak() {
    }

    public HardLineBreak(BasedSequence chars) {
        super(chars);
    }
}
