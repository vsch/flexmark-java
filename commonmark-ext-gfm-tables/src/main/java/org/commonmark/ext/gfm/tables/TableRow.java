package org.commonmark.ext.gfm.tables;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Visitor;

/**
 * Table row of a {@link TableHead} or {@link TableBody} containing {@link TableCell TableCells}.
 */
public class TableRow extends CustomNode {
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
