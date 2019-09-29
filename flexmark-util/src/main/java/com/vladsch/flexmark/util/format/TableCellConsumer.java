package com.vladsch.flexmark.util.format;

public interface TableCellConsumer {
    /**
     * loop over table row cells
     *
     * @param cell       cell
     * @param cellIndex  cell's index in row cells
     * @param cellColumn cell's starting column (if previous cells had column spans not same as cell index)
     */

    void accept(TableCell cell, int cellIndex, int cellColumn);
}
