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

import com.vladsch.flexmark.util.sequence.SequenceUtils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;

@SuppressWarnings({ "WeakerAccess" })
public class ColorStyler extends HtmlStylerBase<Color> {
    @Override
    public String getStyle(Color item) {
        if (item instanceof BackgroundColor) return String.format("background-color:#%s", ColorStyler.getColorValue(item));
        else return item == null ? "" : String.format("color:#%s", getColorValue(item));
    }

    public static Color getNamedColor(String colorName) {
        if (colorName.startsWith("#")) {
            // extract rgb from it
            Integer color = SequenceUtils.parseIntOrNull(colorName.substring(1), 16);
            if (color == null) return null;
            String colorText = colorName.substring(1);
            String r = "";
            String g = "";
            String b = "";
            String a = "";

            switch (colorText.length()) {
                case 3:
                    r = colorText.substring(0, 1);
                    g = colorText.substring(1, 2);
                    b = colorText.substring(2, 3);
                    break;

                case 4:
                    r = colorText.substring(0, 1);
                    g = colorText.substring(1, 2);
                    b = colorText.substring(2, 3);
                    a = colorText.substring(3, 4);
                    break;

                case 6:
                    r = colorText.substring(0, 2);
                    g = colorText.substring(2, 4);
                    b = colorText.substring(4, 6);
                    break;

                case 8:
                    r = colorText.substring(0, 2);
                    g = colorText.substring(2, 4);
                    b = colorText.substring(4, 6);
                    b = colorText.substring(6, 8);
                    break;

                default:
                    return null;
            }

            if (r.length() == 1) r += r;
            if (g.length() == 1) g += g;
            if (b.length() == 1) b += b;
            if (a.length() == 1) a += a;

            if (a.isEmpty()) {
                a = "ff";
            }

            return new Color(parse(r), parse(g), parse(b), parse(a));
        }

        Integer rgb = nameColorMap.get(colorName);
        return rgb == null ? null : new Color(rgb);
    }

    static int parse(String color) {
        return Integer.parseInt(color, 16);
    }

    public static String getColorName(Color item) {
        return item != null ? colorNameMap.get(item.getRGB() & 0x00ffffff) : null;
    }

    public static String getColorValue(Color item) {
        return item == null ? "" : item.getAlpha() != 255 ? String.format(Locale.US, "rgba(%d,%d,%d,%d)", item.getRed(), item.getGreen(), item.getBlue(), item.getAlpha())
                : String.format(Locale.US, "%02x%02x%02x", item.getRed(), item.getGreen(), item.getBlue());
    }

    @SuppressWarnings("WeakerAccess")

    public static String getColorNameOrRGB(Color item) {
        if (item != null) {
            int rgb = item.getRGB();
            String colorName = colorNameMap.get(rgb & 0x00ffffff);
            return colorName != null ? colorName : String.format(Locale.US, "rgb(%d,%d,%d)", item.getRed(), item.getGreen(), item.getBlue());
        }
        return "";
    }

    final public static HashMap<Integer, String> colorNameMap = new HashMap<>();
    final public static HashMap<String, Integer> nameColorMap = new HashMap<>();

    private static void addColorName(int rgb, String name) {
        colorNameMap.put(rgb, name);
        nameColorMap.put(name, rgb);
    }

    static {
        addColorName(0x000000, "black");
        addColorName(0x000080, "navy");
        addColorName(0x00008b, "darkblue");
        addColorName(0x0000cd, "mediumblue");
        addColorName(0x0000ff, "blue");
        addColorName(0x006400, "darkgreen");
        addColorName(0x008000, "green");
        addColorName(0x008080, "teal");
        addColorName(0x008b8b, "darkcyan");
        addColorName(0x00bfff, "deepskyblue");
        addColorName(0x00ced1, "darkturquoise");
        addColorName(0x00fa9a, "mediumspringgreen");
        addColorName(0x00ff00, "lime");
        addColorName(0x00ff7f, "springgreen");
        addColorName(0x00ffff, "aqua");
        addColorName(0x191970, "midnightblue");
        addColorName(0x1e90ff, "dodgerblue");
        addColorName(0x20b2aa, "lightseagreen");
        addColorName(0x228b22, "forestgreen");
        addColorName(0x2e8b57, "seagreen");
        addColorName(0x2f4f4f, "darkslategray");
        addColorName(0x2f4f4f, "darkslategrey");
        addColorName(0x32cd32, "limegreen");
        addColorName(0x3cb371, "mediumseagreen");
        addColorName(0x40e0d0, "turquoise");
        addColorName(0x4169e1, "royalblue");
        addColorName(0x4682b4, "steelblue");
        addColorName(0x483d8b, "darkslateblue");
        addColorName(0x48d1cc, "mediumturquoise");
        addColorName(0x4b0082, "indigo");
        addColorName(0x556b2f, "darkolivegreen");
        addColorName(0x5f9ea0, "cadetblue");
        addColorName(0x6495ed, "cornflowerblue");
        addColorName(0x663399, "rebeccapurple");
        addColorName(0x66cdaa, "mediumaquamarine");
        addColorName(0x696969, "dimgray");
        addColorName(0x696969, "dimgrey");
        addColorName(0x6a5acd, "slateblue");
        addColorName(0x6b8e23, "olivedrab");
        addColorName(0x708090, "slategray");
        addColorName(0x708090, "slategrey");
        addColorName(0x778899, "lightslategray");
        addColorName(0x778899, "lightslategrey");
        addColorName(0x7b68ee, "mediumslateblue");
        addColorName(0x7cfc00, "lawngreen");
        addColorName(0x7fff00, "chartreuse");
        addColorName(0x7fffd4, "aquamarine");
        addColorName(0x800000, "maroon");
        addColorName(0x800080, "purple");
        addColorName(0x808000, "olive");
        addColorName(0x808080, "gray");
        addColorName(0x808080, "grey");
        addColorName(0x87ceeb, "skyblue");
        addColorName(0x87cefa, "lightskyblue");
        addColorName(0x8a2be2, "blueviolet");
        addColorName(0x8b0000, "darkred");
        addColorName(0x8b008b, "darkmagenta");
        addColorName(0x8b4513, "saddlebrown");
        addColorName(0x8fbc8f, "darkseagreen");
        addColorName(0x90ee90, "lightgreen");
        addColorName(0x9370db, "mediumpurple");
        addColorName(0x9400d3, "darkviolet");
        addColorName(0x98fb98, "palegreen");
        addColorName(0x9932cc, "darkorchid");
        addColorName(0x9acd32, "yellowgreen");
        addColorName(0xa0522d, "sienna");
        addColorName(0xa52a2a, "brown");
        addColorName(0xa9a9a9, "darkgray");
        addColorName(0xa9a9a9, "darkgrey");
        addColorName(0xadd8e6, "lightblue");
        addColorName(0xadff2f, "greenyellow");
        addColorName(0xafeeee, "paleturquoise");
        addColorName(0xb0c4de, "lightsteelblue");
        addColorName(0xb0e0e6, "powderblue");
        addColorName(0xb22222, "firebrick");
        addColorName(0xb8860b, "darkgoldenrod");
        addColorName(0xba55d3, "mediumorchid");
        addColorName(0xbc8f8f, "rosybrown");
        addColorName(0xbdb76b, "darkkhaki");
        addColorName(0xc0c0c0, "silver");
        addColorName(0xc71585, "mediumvioletred");
        addColorName(0xcd5c5c, "indianred");
        addColorName(0xcd853f, "peru");
        addColorName(0xd2691e, "chocolate");
        addColorName(0xd2b48c, "tan");
        addColorName(0xd3d3d3, "lightgray");
        addColorName(0xd3d3d3, "lightgrey");
        addColorName(0xd8bfd8, "thistle");
        addColorName(0xda70d6, "orchid");
        addColorName(0xdaa520, "goldenrod");
        addColorName(0xdb7093, "palevioletred");
        addColorName(0xdc143c, "crimson");
        addColorName(0xdcdcdc, "gainsboro");
        addColorName(0xdda0dd, "plum");
        addColorName(0xdeb887, "burlywood");
        addColorName(0xe0ffff, "lightcyan");
        addColorName(0xe6e6fa, "lavender");
        addColorName(0xe9967a, "darksalmon");
        addColorName(0xee82ee, "violet");
        addColorName(0xeee8aa, "palegoldenrod");
        addColorName(0xf08080, "lightcoral");
        addColorName(0xf0e68c, "khaki");
        addColorName(0xf0f8ff, "aliceblue");
        addColorName(0xf0fff0, "honeydew");
        addColorName(0xf0ffff, "azure");
        addColorName(0xf4a460, "sandybrown");
        addColorName(0xf5deb3, "wheat");
        addColorName(0xf5f5dc, "beige");
        addColorName(0xf5f5f5, "whitesmoke");
        addColorName(0xf5fffa, "mintcream");
        addColorName(0xf8f8ff, "ghostwhite");
        addColorName(0xfa8072, "salmon");
        addColorName(0xfaebd7, "antiquewhite");
        addColorName(0xfaf0e6, "linen");
        addColorName(0xfafad2, "lightgoldenrodyellow");
        addColorName(0xfdf5e6, "oldlace");
        addColorName(0xff0000, "red");
        addColorName(0xff00ff, "fuchsia");
        addColorName(0xff1493, "deeppink");
        addColorName(0xff4500, "orangered");
        addColorName(0xff6347, "tomato");
        addColorName(0xff69b4, "hotpink");
        addColorName(0xff7f50, "coral");
        addColorName(0xff8c00, "darkorange");
        addColorName(0xffa07a, "lightsalmon");
        addColorName(0xffa500, "orange");
        addColorName(0xffb6c1, "lightpink");
        addColorName(0xffc0cb, "pink");
        addColorName(0xffd700, "gold");
        addColorName(0xffdab9, "peachpuff");
        addColorName(0xffdead, "navajowhite");
        addColorName(0xffe4b5, "moccasin");
        addColorName(0xffe4c4, "bisque");
        addColorName(0xffe4e1, "mistyrose");
        addColorName(0xffebcd, "blanchedalmond");
        addColorName(0xffefd5, "papayawhip");
        addColorName(0xfff0f5, "lavenderblush");
        addColorName(0xfff5ee, "seashell");
        addColorName(0xfff8dc, "cornsilk");
        addColorName(0xfffacd, "lemonchiffon");
        addColorName(0xfffaf0, "floralwhite");
        addColorName(0xfffafa, "snow");
        addColorName(0xffff00, "yellow");
        addColorName(0xffffe0, "lightyellow");
        addColorName(0xfffff0, "ivory");
        addColorName(0xffffff, "white");
    }
}
