package com.vladsch.flexmark.util.format;

public final class ColumnSort {
  final int column;
  final Sort sort;

  private ColumnSort(int column, Sort sort) {
    this.column = column;
    this.sort = sort;
  }

  public static ColumnSort columnSort(
      int column, boolean descending, boolean numeric, boolean numericLast) {
    if (numeric) {
      if (numericLast) {
        return new ColumnSort(
            column, descending ? Sort.DESCENDING_NUMERIC_LAST : Sort.ASCENDING_NUMERIC_LAST);
      }

      return new ColumnSort(column, descending ? Sort.DESCENDING_NUMERIC : Sort.ASCENDING_NUMERIC);
    }

    return new ColumnSort(column, descending ? Sort.DESCENDING : Sort.ASCENDING);
  }
}
