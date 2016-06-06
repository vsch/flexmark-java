package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * Table cell of a {@link TableRow} containing inline nodes.
 */
public class TableCell extends CustomNode {

    private boolean header;
    private Alignment alignment;

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableCell() {
    }

    public TableCell(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return whether the cell is a header or not
     */
    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    /**
     * @return the cell alignment
     */
    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * How the cell is aligned horizontally.
     */
    public enum Alignment {
        LEFT, CENTER, RIGHT
    }

}
