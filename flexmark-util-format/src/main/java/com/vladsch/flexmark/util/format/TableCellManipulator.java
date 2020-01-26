package com.vladsch.flexmark.util.format;

public interface TableCellManipulator {
    int BREAK = Integer.MIN_VALUE;

    /**
     * loop over table row cells
     *
     * @param cell         cell
     * @param cellIndex    cell's index in row cells
     * @param cellColumn   cell's starting column (if previous cells had column spans not same as cell index)
     * @param allCellIndex cell's index as would be at the beginning of the request, deleted cell indices skipped
     * @return change to cells &lt;0 of cells deleted, &gt;0 number inserted, 0 no change, or BREAK to stop processing cells
     */

    int apply(TableCell cell, int cellIndex, int cellColumn, int allCellIndex);
}
