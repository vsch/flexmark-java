package com.vladsch.flexmark.util.format;

import java.util.ArrayList;
import java.util.List;

public class TableSection {
  final TableSectionType sectionType;
  public final List<TableRow> rows = new ArrayList<>();
  protected int row;
  protected int column;

  TableSection(TableSectionType sectionType) {
    this.sectionType = sectionType;

    row = 0;
    column = 0;
  }

  public List<TableRow> getRows() {
    return rows;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  void nextRow() {
    row++;
    column = 0;
  }

  void normalize() {
    for (TableRow row : rows) {
      row.normalize();
    }
  }

  private TableRow expandTo(int row) {
    while (row >= rows.size()) {
      TableRow tableRow = defaultRow();
      rows.add(tableRow);
    }
    return rows.get(row);
  }

  TableRow expandTo(int row, int column) {
    return expandTo(row, column, null);
  }

  private TableRow expandTo(int row, int column, TableCell cell) {
    while (row >= rows.size()) {
      TableRow tableRow = defaultRow();
      tableRow.expandTo(column, cell);
      rows.add(tableRow);
    }
    return rows.get(row).expandTo(column);
  }

  public TableRow defaultRow() {
    return new TableRow();
  }

  public TableCell defaultCell() {
    return TableCell.NULL;
  }

  TableRow get(int row) {
    return expandTo(row);
  }

  public int getMaxColumns() {
    int columns = 0;
    for (TableRow tableRow : rows) {
      int spans = tableRow.getSpannedColumns();
      if (columns < spans) columns = spans;
    }
    return columns;
  }

  public int getMinColumns() {
    int columns = 0;
    for (TableRow tableRow : rows) {
      int spans = tableRow.getSpannedColumns();
      if (columns > spans || columns == 0) columns = spans;
    }
    return columns;
  }

  private CharSequence dumpRows() {
    StringBuilder sb = new StringBuilder();
    for (TableRow row : rows) {
      sb.append("  ").append(row.toString()).append("\n");
    }
    return sb;
  }

  @Override
  public String toString() {
    // NOTE: show not simple name but name of container class if any
    return this.getClass().getName().substring(getClass().getPackage().getName().length() + 1)
        + "["
        + "sectionType="
        + sectionType
        + ", rows=[\n"
        + dumpRows()
        + ']';
  }
}
