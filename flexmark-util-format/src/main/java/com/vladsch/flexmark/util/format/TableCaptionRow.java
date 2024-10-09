package com.vladsch.flexmark.util.format;

class TableCaptionRow extends TableRow {
  @Override
  public TableCell defaultCell() {
    return TableCaptionSection.NULL_CELL;
  }
}
