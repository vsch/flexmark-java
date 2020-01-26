package com.vladsch.flexmark.util.format;

import org.jetbrains.annotations.NotNull;

final public class ColumnSort {
    final public int column;
    final public @NotNull Sort sort;

    private ColumnSort(int column, @NotNull Sort sort) {
        this.column = column;
        this.sort = sort;
    }

    @NotNull
    public static ColumnSort columnSort(int column, @NotNull Sort sort) {
        return new ColumnSort(column, sort);
    }

    @NotNull
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
