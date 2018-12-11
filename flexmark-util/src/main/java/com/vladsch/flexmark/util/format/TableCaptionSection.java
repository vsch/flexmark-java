package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TableCaptionSection extends TableSection {
    public static final TableCell NULL_CELL = new TableCell(BasedSequence.NULL, BasedSequence.NULL, BasedSequence.NULL, 1, 0);
    public static final TableCell DEFAULT_CELL = new TableCell("[", "", "]", 1, 1);

    public TableCaptionSection(final TableSectionType sectionType) {
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
