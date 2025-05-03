package com.vladsch.flexmark.ext.d2;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class D2Value extends Node {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public D2Value() {
    }

    public D2Value(BasedSequence chars) {
        super(chars);
    }
}
