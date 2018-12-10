package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Body part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableSeparator extends CustomNode implements DoNotDecorate {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableSeparator() {
    }

    public TableSeparator(BasedSequence chars) {
        super(chars);
    }
}
