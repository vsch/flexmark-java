package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;

/**
 * Head part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableHead extends CustomNode<TableVisitor> {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableHead() {
    }

    public TableHead(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(TableVisitor visitor) {
        visitor.visit(this);
    }
}
