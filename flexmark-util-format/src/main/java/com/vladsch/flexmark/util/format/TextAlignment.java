package com.vladsch.flexmark.util.format;

public enum TextAlignment {
  LEFT,
  CENTER,
  RIGHT,
  JUSTIFIED;

  public static TextAlignment getAlignment(String alignment) {
    for (TextAlignment cellAlignment : values()) {
      if (cellAlignment.name().equalsIgnoreCase(alignment)) {
        return cellAlignment;
      }
    }
    return LEFT;
  }
}
