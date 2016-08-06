package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * A sim toc contents ast containing all text that came after the sim toc ast
 */
public class SimTocContent extends CustomBlock implements DoNotDecorate {
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
}
