package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.util.ast.CustomNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

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

}
