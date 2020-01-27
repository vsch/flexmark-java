package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TableCaptionSection extends TableSection {
    final public static TableCell NULL_CELL = new TableCell(null, BasedSequence.NULL, BasedSequence.NULL, BasedSequence.NULL, 1, 0);
    final public static TableCell DEFAULT_CELL = new TableCell(null, "[", "", "]", 1, 1);

    public TableCaptionSection(TableSectionType sectionType) {
        super(sectionType);
    }

    @Override
    public TableRow defaultRow() {
        return new TableCaptionRow();
    }

    @Override
    public TableCell defaultCell() {
        return DEFAULT_CELL;
    }
}
