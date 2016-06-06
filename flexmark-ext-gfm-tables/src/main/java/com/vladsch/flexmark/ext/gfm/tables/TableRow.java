package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * Table row of a {@link TableHead} or {@link TableBody} containing {@link TableCell TableCells}.
 */
public class TableRow extends CustomNode {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableRow() {
    }

    public TableRow(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
