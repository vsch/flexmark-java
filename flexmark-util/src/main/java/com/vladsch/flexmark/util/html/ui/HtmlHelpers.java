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

import javax.swing.JTextPane;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.util.misc.Utils.*;

public class HtmlHelpers {
    public static String toHtmlError(String err, boolean withContext) {
        if (err == null) return null;

        if (withContext) {
            Matcher matcher = Pattern.compile("(?:^|\n)(.*\n)(\\s*)\\^(\n?)$").matcher(err);
            if (matcher.find()) {
                String group = matcher.group(2);
                if (group != null && !group.isEmpty()) {
                    int prevLineStart = matcher.group(1) != null ? matcher.start(1) : matcher.start(2);
                    String lastLine = Utils.repeat("&nbsp;", group.length());
                    err = err.substring(0, prevLineStart) + "<span style=\"font-family:monospaced\">" + err.substring(prevLineStart, matcher.start(2)).replace(" ", "&nbsp;") + lastLine + "^</span>" + group;
                }
            }
        }
        return err.replace("\n", "<br>");
    }

    public static void setRegExError(String error, JTextPane jTextPane, Font textFont, BackgroundColor validTextFieldBackground, BackgroundColor warningTextFieldBackground) {
        HtmlBuilder html = new HtmlBuilder();
        html.tag("html").style("margin:2px;vertical-align:middle;").attr(validTextFieldBackground, textFont).tag("body");
        html.attr(warningTextFieldBackground).tag("div");
        html.append(toHtmlError(error, true));
        html.closeTag("div");
        html.closeTag("body");
        html.closeTag("html");

        jTextPane.setVisible(true);
        jTextPane.setText(html.toFinalizedString());
        jTextPane.revalidate();
        jTextPane.getParent().revalidate();
        jTextPane.getParent().getParent().revalidate();
    }

    public static String withContext(String text, String context, int pos, String prefix, String suffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(text).append('\n');
        sb.append(prefix).append(context).append(suffix).append('\n');
        for (int i = 1; i < prefix.length(); i++) sb.append(' ');
        sb.append('^').append('\n');
        return sb.toString();
    }

    public static String toRgbString(java.awt.Color color) {
        return (color == null) ? "rgb(0,0,0)" : "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
    }

    public static String toHtmlString(java.awt.Color color) {
        return (color == null) ? "#000000" : String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static java.awt.Color mixedColor(java.awt.Color originalColor, java.awt.Color overlayColor) {
        float[] hsbColor = java.awt.Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), new float[3]);
        float[] hsbError = java.awt.Color.RGBtoHSB(overlayColor.getRed(), overlayColor.getGreen(), overlayColor.getBlue(), new float[3]);
        float[] hsbMixed = new float[3];

        // kotlin code
        //hsbMixed[0] = hsbError[0]
        //hsbMixed[1] = hsbColor[1].rangeLimit(hsbError[1].max(0.3f).min(0.5f), 1.0f)
        //hsbMixed[2] = hsbColor[2].rangeLimit(hsbError[2].max(0.3f).min(0.5f), 1.0f)
        //return Color.getHSBColor(hsbMixed[0], hsbMixed[1], hsbMixed[2])

        // incorrect translation from kotlin
        //hsbMixed[0] = hsbError[0];
        //hsbMixed[1] = min(max(rangeLimit(hsbColor[1], hsbError[1], 0.3f), 0.5f), 1.0f);
        //hsbMixed[2] = min(max(rangeLimit(hsbColor[2], hsbError[2], 0.3f), 0.5f), 1.0f);
        //return java.awt.Color.getHSBColor(hsbMixed[0], hsbMixed[1], hsbMixed[2]);

        hsbMixed[0] = hsbError[0];
        hsbMixed[1] = rangeLimit(hsbColor[1], min(max(hsbError[1], 0.3f), 0.5f), 1.0f);
        hsbMixed[2] = rangeLimit(hsbColor[2], min(max(hsbError[2], 0.3f), 0.5f), 1.0f);
        return java.awt.Color.getHSBColor(hsbMixed[0], hsbMixed[1], hsbMixed[2]);
    }
}
