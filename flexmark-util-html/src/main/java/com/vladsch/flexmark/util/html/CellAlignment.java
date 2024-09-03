package com.vladsch.flexmark.util.html;

public enum CellAlignment {
  NONE,
  LEFT,
  CENTER,
  RIGHT;

  public static CellAlignment getAlignment(String alignment) {
    for (CellAlignment cellAlignment : values()) {
      if (cellAlignment.name().equalsIgnoreCase(alignment)) {
        return cellAlignment;
      }
    }
    return NONE;
  }
}
