package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * Body part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableBody extends CustomNode {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableBody() {
    }

    public TableBody(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
