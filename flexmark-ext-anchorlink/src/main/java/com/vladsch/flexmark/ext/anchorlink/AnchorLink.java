package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
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
