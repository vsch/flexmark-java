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

import java.awt.Color;

public class BackgroundColor extends Color {
  private static final long serialVersionUID = 1L;
  public static final BackgroundColor NULL = new BackgroundColor(new Color(0, true));
  public static final BackgroundColor WHITE = new BackgroundColor(Color.WHITE);
  public static final BackgroundColor LIGHT_GRAY = new BackgroundColor(Color.LIGHT_GRAY);
  public static final BackgroundColor GRAY = new BackgroundColor(Color.GRAY);
  public static final BackgroundColor DARK_GRAY = new BackgroundColor(Color.DARK_GRAY);
  public static final BackgroundColor BLACK = new BackgroundColor(Color.BLACK);
  public static final BackgroundColor RED = new BackgroundColor(Color.RED);
  public static final BackgroundColor PINK = new BackgroundColor(Color.PINK);
  public static final BackgroundColor ORANGE = new BackgroundColor(Color.ORANGE);
  public static final BackgroundColor YELLOW = new BackgroundColor(Color.YELLOW);
  public static final BackgroundColor GREEN = new BackgroundColor(Color.GREEN);
  public static final BackgroundColor MAGENTA = new BackgroundColor(Color.MAGENTA);
  public static final BackgroundColor CYAN = new BackgroundColor(Color.CYAN);
  public static final BackgroundColor BLUE = new BackgroundColor(Color.BLUE);

  protected BackgroundColor(Color other) {
    super(other.getRGB());
  }

  protected BackgroundColor(int rgb) {
    super(rgb);
  }

  public static BackgroundColor of(Color color) {
    return new BackgroundColor(color);
  }

  public static BackgroundColor of(int rgb) {
    return new BackgroundColor(rgb);
  }

  public static BackgroundColor of(String colorName) {
    Color color = ColorStyler.getNamedColor(colorName);
    return color == null ? NULL : new BackgroundColor(color);
  }
}
