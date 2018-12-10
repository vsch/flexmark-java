package com.vladsch.flexmark.util.format;

public class TableSeparatorRow extends TableRow {
    @Override
    public TableCell defaultCell() {
        return new TableCell("---", 1, 1);
    }
}
