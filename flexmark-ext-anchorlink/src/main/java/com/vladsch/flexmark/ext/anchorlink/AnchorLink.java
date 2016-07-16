package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class AnchorLink extends CustomNode {
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
