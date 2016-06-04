package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * Head part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableHead extends CustomNode {
    public TableHead() {
    }

    public TableHead(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
