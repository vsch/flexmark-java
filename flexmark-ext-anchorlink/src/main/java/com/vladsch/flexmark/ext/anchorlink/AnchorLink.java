package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Anchor link node
 */
public class AnchorLink extends Node {
    public AnchorLink() {
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
    }
}
