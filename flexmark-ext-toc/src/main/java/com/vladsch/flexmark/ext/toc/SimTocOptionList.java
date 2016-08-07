package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A sim toc contents node containing all text that came after the sim toc node
 */
public class SimTocOptionList extends CustomNode implements DoNotDecorate {
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return EMPTY_SEGMENTS;
    }

    public SimTocOptionList() {
    }

    public SimTocOptionList(BasedSequence chars) {
        super(chars);
    }
}
