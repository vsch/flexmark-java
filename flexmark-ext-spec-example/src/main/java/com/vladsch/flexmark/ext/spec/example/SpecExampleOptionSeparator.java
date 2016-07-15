package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;

/**
 * A SpecExample block node
 */
public class SpecExampleOptionSeparator extends CustomNode<SpecExampleVisitor> {
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

    @Override
    public void accept(SpecExampleVisitor visitor) {
        visitor.visit(this);
    }
}
