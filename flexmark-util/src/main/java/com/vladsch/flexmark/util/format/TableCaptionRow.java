package com.vladsch.flexmark.util.format;

public class TableCaptionRow extends TableRow {

    @Override
    public TableCell defaultCell() {
        return TableCaptionSection.NULL_CELL;
    }
}
