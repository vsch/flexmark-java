/*
 * Copyright (c) 2016-2018 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vladsch.flexmark.util.html.ui;

public class Color extends java.awt.Color {
  public static final Color NULL = new Color(new java.awt.Color(0, true));
  public static final Color WHITE = new Color(java.awt.Color.WHITE);
  public static final Color LIGHT_GRAY = new Color(java.awt.Color.LIGHT_GRAY);
  public static final Color GRAY = new Color(java.awt.Color.GRAY);
  public static final Color DARK_GRAY = new Color(java.awt.Color.DARK_GRAY);
  public static final Color BLACK = new Color(java.awt.Color.BLACK);
  public static final Color RED = new Color(java.awt.Color.RED);
  public static final Color PINK = new Color(java.awt.Color.PINK);
  public static final Color ORANGE = new Color(java.awt.Color.ORANGE);
  public static final Color YELLOW = new Color(java.awt.Color.YELLOW);
  public static final Color GREEN = new Color(java.awt.Color.GREEN);
  public static final Color MAGENTA = new Color(java.awt.Color.MAGENTA);
  public static final Color CYAN = new Color(java.awt.Color.CYAN);
  public static final Color BLUE = new Color(java.awt.Color.BLUE);

  protected Color(java.awt.Color other) {
    super(other.getRGB());
  }

  protected Color(int rgb) {
    super(rgb);
  }

  public static Color of(java.awt.Color color) {
    return new Color(color);
  }

  public static Color of(int rgb) {
    return new Color(rgb);
  }

  public static Color of(String colorName) {
    java.awt.Color color = ColorStyler.getNamedColor(colorName);
    return color == null ? NULL : new Color(color);
  }

  @Override
  public String toString() {
    return "Color { " + HtmlHelpers.toHtmlString(this) + " " + HtmlHelpers.toRgbString(this) + "}";
  }
}
