package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A SpecExample block ast
 */
public class SpecExampleHtml extends CustomNode {
    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public SpecExampleHtml() {
    }

    public SpecExampleHtml(BasedSequence chars) {
        super(chars);
    }
}
