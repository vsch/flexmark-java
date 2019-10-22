package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A SpecExample block node
 */
public class SpecExampleSeparator extends Node {
    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        astExtraChars(out);
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public SpecExampleSeparator() {
    }

    public SpecExampleSeparator(BasedSequence chars) {
        super(chars);
    }
}
