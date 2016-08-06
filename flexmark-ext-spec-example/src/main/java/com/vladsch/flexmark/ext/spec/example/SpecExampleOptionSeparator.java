package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A SpecExample block ast
 */
public class SpecExampleOptionSeparator extends CustomNode {
    @Override
    public void getAstExtra(StringBuilder out) {
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public SpecExampleOptionSeparator() {
    }

    public SpecExampleOptionSeparator(BasedSequence chars) {
        super(chars);
    }
}
