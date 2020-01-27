package com.vladsch.flexmark.util.format;

public class TableSeparatorSection extends TableSection {
    final public static TableCell DEFAULT_CELL = new TableCell("---", 1, 1);

    public TableSeparatorSection(TableSectionType sectionType) {
        super(sectionType);
    }

    @Override
    public TableRow defaultRow() {
        return new TableSeparatorRow();
    }

    @Override
    public TableCell defaultCell() {
        return DEFAULT_CELL;
    }
}
