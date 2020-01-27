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

import java.awt.Font;

@SuppressWarnings("WeakerAccess")
public class FontStyle {
    final public static FontStyle PLAIN = new FontStyle(0);
    final public static FontStyle BOLD = new FontStyle(Font.BOLD);
    final public static FontStyle ITALIC = new FontStyle(Font.ITALIC);
    final public static FontStyle BOLD_ITALIC = new FontStyle(Font.ITALIC | Font.BOLD);

    final public int fontStyle;

    private FontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public boolean isItalic() {
        return (fontStyle & Font.ITALIC) != 0;
    }

    public boolean isBold() {
        return (fontStyle & Font.BOLD) != 0;
    }

    public static FontStyle of(int fontStyle) {
        if ((fontStyle & (Font.BOLD | Font.ITALIC)) == (Font.BOLD | Font.ITALIC)) return BOLD_ITALIC;
        if ((fontStyle & (Font.BOLD)) == (Font.BOLD)) return BOLD;
        if ((fontStyle & (Font.ITALIC)) == (Font.ITALIC)) return ITALIC;
        return PLAIN;
    }
}
