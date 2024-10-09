package com.vladsch.flexmark.util.format;

class TableSeparatorRow extends TableRow {
  @Override
  public TableCell defaultCell() {
    return TableSeparatorSection.DEFAULT_CELL;
  }
}
