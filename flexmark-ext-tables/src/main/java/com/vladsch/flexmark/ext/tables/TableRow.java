package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.LineBreakNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * Table row of a {@link TableHead} or {@link TableBody} containing {@link TableCell TableCells}.
 */
public class TableRow extends Node implements LineBreakNode {
    /**
     * rowNumber within the table section: header, body, separator
     */
    private int rowNumber;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        super.getAstExtra(out);
        if (rowNumber != 0) out.append(" rowNumber=").append(rowNumber);
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableRow() {
    }

    public TableRow(BasedSequence chars) {
        super(chars);
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
