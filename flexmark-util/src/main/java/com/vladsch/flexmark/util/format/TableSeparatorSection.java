package com.vladsch.flexmark.util.format;

public class TableSeparatorSection extends TableSection {
    @Override
    protected TableRow defaultRow() {
        return new TableSeparatorRow();
    }
}
