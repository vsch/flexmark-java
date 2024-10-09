package com.vladsch.flexmark.util.format;

enum Sort {
  ASCENDING,
  DESCENDING,
  ASCENDING_NUMERIC,
  DESCENDING_NUMERIC,
  ASCENDING_NUMERIC_LAST,
  DESCENDING_NUMERIC_LAST,
  ;

  boolean isDescending() {
    return this == DESCENDING || this == DESCENDING_NUMERIC || this == DESCENDING_NUMERIC_LAST;
  }

  boolean isNumeric() {
    return this == ASCENDING_NUMERIC
        || this == ASCENDING_NUMERIC_LAST
        || this == DESCENDING_NUMERIC
        || this == DESCENDING_NUMERIC_LAST;
  }

  boolean isNumericLast() {
    return this == ASCENDING_NUMERIC_LAST || this == DESCENDING_NUMERIC_LAST;
  }
}
