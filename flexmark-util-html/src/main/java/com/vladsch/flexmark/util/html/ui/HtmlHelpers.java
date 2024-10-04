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

import com.vladsch.flexmark.util.misc.Utils;

public class HtmlHelpers {
  public static String toRgbString(java.awt.Color color) {
    return (color == null)
        ? "rgb(0,0,0)"
        : "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
  }

  public static String toHtmlString(java.awt.Color color) {
    return (color == null)
        ? "#000000"
        : String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
  }

  public static java.awt.Color mixedColor(
      java.awt.Color originalColor, java.awt.Color overlayColor) {
    float[] hsbColor =
        java.awt.Color.RGBtoHSB(
            originalColor.getRed(),
            originalColor.getGreen(),
            originalColor.getBlue(),
            new float[3]);
    float[] hsbError =
        java.awt.Color.RGBtoHSB(
            overlayColor.getRed(), overlayColor.getGreen(), overlayColor.getBlue(), new float[3]);
    float[] hsbMixed = new float[3];

    hsbMixed[0] = hsbError[0];
    hsbMixed[1] =
        Utils.rangeLimit(hsbColor[1], Utils.min(Utils.max(hsbError[1], 0.3f), 0.5f), 1.0f);
    hsbMixed[2] =
        Utils.rangeLimit(hsbColor[2], Utils.min(Utils.max(hsbError[2], 0.3f), 0.5f), 1.0f);
    return java.awt.Color.getHSBColor(hsbMixed[0], hsbMixed[1], hsbMixed[2]);
  }
}
