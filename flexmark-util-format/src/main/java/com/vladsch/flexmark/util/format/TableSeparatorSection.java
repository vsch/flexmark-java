package com.vladsch.flexmark.util.format;

class TableSeparatorSection extends TableSection {
  static final TableCell DEFAULT_CELL = new TableCell("---", 1, 1);

  TableSeparatorSection(TableSectionType sectionType) {
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
