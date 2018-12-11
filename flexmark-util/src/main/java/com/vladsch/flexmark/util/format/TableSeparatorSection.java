package com.vladsch.flexmark.util.format;

public class TableSeparatorSection extends TableSection {
    public static final TableCell NULL_CELL = new TableCell("", 1, 0);
    public static final TableCell DEFAULT_CELL = new TableCell("---", 1, 1);

    public TableSeparatorSection(final TableSectionType sectionType) {
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
