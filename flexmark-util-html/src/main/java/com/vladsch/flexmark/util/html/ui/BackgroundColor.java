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

@SuppressWarnings({ "WeakerAccess" })
public class BackgroundColor extends Color {
    final public static BackgroundColor NULL = new BackgroundColor(new Color(0, true));
    final public static BackgroundColor WHITE = new BackgroundColor(Color.WHITE);
    final public static BackgroundColor LIGHT_GRAY = new BackgroundColor(Color.LIGHT_GRAY);
    final public static BackgroundColor GRAY = new BackgroundColor(Color.GRAY);
    final public static BackgroundColor DARK_GRAY = new BackgroundColor(Color.DARK_GRAY);
    final public static BackgroundColor BLACK = new BackgroundColor(Color.BLACK);
    final public static BackgroundColor RED = new BackgroundColor(Color.RED);
    final public static BackgroundColor PINK = new BackgroundColor(Color.PINK);
    final public static BackgroundColor ORANGE = new BackgroundColor(Color.ORANGE);
    final public static BackgroundColor YELLOW = new BackgroundColor(Color.YELLOW);
    final public static BackgroundColor GREEN = new BackgroundColor(Color.GREEN);
    final public static BackgroundColor MAGENTA = new BackgroundColor(Color.MAGENTA);
    final public static BackgroundColor CYAN = new BackgroundColor(Color.CYAN);
    final public static BackgroundColor BLUE = new BackgroundColor(Color.BLUE);

    protected BackgroundColor(Color other) { super(other.getRGB()); }

    protected BackgroundColor(int rgb) { super(rgb); }

    public static BackgroundColor of(Color color) { return new BackgroundColor(color); }

    public static BackgroundColor of(int rgb) { return new BackgroundColor(rgb); }

    public static BackgroundColor of(String colorName) {
        Color color = ColorStyler.getNamedColor(colorName);
        return color == null ? NULL : new BackgroundColor(color);
    }
}
