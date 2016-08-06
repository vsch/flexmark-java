package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Head part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableHead extends CustomNode {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableHead() {
    }

    public TableHead(BasedSequence chars) {
        super(chars);
    }
}
