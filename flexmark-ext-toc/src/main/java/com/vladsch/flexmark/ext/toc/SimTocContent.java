package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.DoNotLinkify;

import java.util.List;

/**
 * A sim toc contents node containing all text that came after the sim toc node
 */
public class SimTocContent extends CustomBlock<SimTocVisitor> implements DoNotLinkify {
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
    }

    public SimTocContent() {
    }

    public SimTocContent(BasedSequence chars) {
        super(chars);
    }

    public SimTocContent(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public SimTocContent(List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public SimTocContent(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(SimTocVisitor visitor) {
        visitor.visit(this);
    }
}
