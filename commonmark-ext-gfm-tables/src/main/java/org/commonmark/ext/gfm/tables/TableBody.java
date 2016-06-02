package org.commonmark.ext.gfm.tables;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Visitor;

/**
 * Body part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableBody extends CustomNode {
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
