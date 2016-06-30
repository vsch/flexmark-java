package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class AnchorLink extends CustomNode {
    private final String headerId;

    public String getHeaderId() {
        return headerId;
    }

    public AnchorLink(String headerId) {
        this.headerId = headerId;
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" headerId: ").append(headerId).append(" ");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
