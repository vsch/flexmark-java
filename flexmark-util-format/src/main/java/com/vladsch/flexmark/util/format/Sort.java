package com.vladsch.flexmark.util.format;

public enum Sort {
    NONE,
    ASCENDING,
    DESCENDING,
    ASCENDING_NUMERIC,
    DESCENDING_NUMERIC,
    ASCENDING_NUMERIC_LAST,
    DESCENDING_NUMERIC_LAST,
    ;

    public boolean isDescending() {
        return this == DESCENDING || this == DESCENDING_NUMERIC || this == DESCENDING_NUMERIC_LAST;
    }

    public boolean isNumeric() {
        return this == ASCENDING_NUMERIC || this == ASCENDING_NUMERIC_LAST || this == DESCENDING_NUMERIC || this == DESCENDING_NUMERIC_LAST;
    }

    public boolean isNumericLast() {
        return this == ASCENDING_NUMERIC_LAST || this == DESCENDING_NUMERIC_LAST;
    }
}
