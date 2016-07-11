package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Visitor;

/**
 * A sim toc contents node containing all text that came after the sim toc node
 */
public class SimTocOption extends CustomNode implements DoNotLinkify {
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    public SimTocOption() {
    }

    public SimTocOption(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
