package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Text;

/**
 * Table cell of a {@link TableRow} containing inline nodes.
 */
public class TableColumnSeparator extends Text {
    public TableColumnSeparator() {
    }

    public TableColumnSeparator(BasedSequence chars) {
        super(chars);
    }

    public TableColumnSeparator(String chars) {
        super(chars);
    }
}
