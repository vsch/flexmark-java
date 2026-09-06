package com.vladsch.flexmark.util.format;


final public class ColumnSort {
    final public int column;
    final public Sort sort;

    private ColumnSort(int column, Sort sort) {
        this.column = column;
        this.sort = sort;
    }

    public static ColumnSort columnSort(int column, Sort sort) {
        return new ColumnSort(column, sort);
    }

    public static ColumnSort columnSort(int column, boolean descending, boolean numeric, boolean numericLast) {
        if (numeric) {
            if (numericLast) {
                return new ColumnSort(column, descending ? Sort.DESCENDING_NUMERIC_LAST : Sort.ASCENDING_NUMERIC_LAST);
            } else {
                return new ColumnSort(column, descending ? Sort.DESCENDING_NUMERIC : Sort.ASCENDING_NUMERIC);
            }
        } else {
            return new ColumnSort(column, descending ? Sort.DESCENDING : Sort.ASCENDING);
        }
    }
}
