package com.vladsch.flexmark.docx.converter.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorNameMapper {
    final private static Map<String, Color> colors = new HashMap<>();
    static {
        colors.put("black", new Color(0x000000));
        colors.put("blue", new Color(0x0000FF));
        colors.put("cyan", new Color(0x00FFFF));
        colors.put("green", new Color(0x008000));
        colors.put("magenta", new Color(0xFF00FF));
        colors.put("red", new Color(0xFF0000));
        colors.put("yellow", new Color(0xFFFF00));
        colors.put("white", new Color(0xFFFFFF));
        colors.put("darkBlue", new Color(0x00008B));
        colors.put("darkCyan", new Color(0x008B8B));
        colors.put("darkGreen", new Color(0x006400));
        colors.put("darkMagenta", new Color(0x8B008B));
        colors.put("darkRed", new Color(0x8B0000));
        colors.put("darkYellow", new Color(0xFFD700));
        colors.put("darkGray", new Color(0xA9A9A9));
        colors.put("lightGray", new Color(0xD3D3D3));
    }
    ;

    final private static String hexPattern = "^[0-9a-fA-F]{6}$";

    /**
     * from: https://stackoverflow.com/questions/6334311/whats-the-best-way-to-round-a-color-object-to-the-nearest-color-constant
     *
     * @param c1 color 1
     * @param c2 color 2
     * @return distance between two colors
     */
    public static double colorDistance(@NotNull Color c1, @NotNull Color c2) {
        int red1 = c1.getRed();
        int red2 = c2.getRed();
        int rmean = (red1 + red2) >> 1;
        int r = red1 - red2;
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        return Math.sqrt((((512 + rmean) * r * r) >> 8) + 4 * g * g + (((767 - rmean) * b * b) >> 8));
    }

    public static String colorToString(@NotNull Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return String.format("%02x%02x%02x", r, g, b);
    }

    @NotNull
    public static Color colorFromString(@NotNull String color) {
        return new Color(
                Integer.valueOf(color.substring(0, 2), 16),
                Integer.valueOf(color.substring(2, 4), 16),
                Integer.valueOf(color.substring(4, 6), 16));
    }

    public static boolean isHexColor(@NotNull String color) {
        return color.matches(hexPattern);
    }

    public static boolean isNamedColor(@NotNull String color) {
        return colors.containsKey(color);
    }

    @Nullable
    public static String getValidNamedOrHexColor(@NotNull String s) {
        if (ColorNameMapper.isNamedColor(s) || ColorNameMapper.isHexColor(s)) {
            return s;
        }
        return null;
    }

    @NotNull
    public static String getValidHexColorOrDefault(@NotNull String s, @NotNull String defaultValue) {
        String hexColor = getValidHexColor(s);
        return hexColor != null ? hexColor : defaultValue;
    }

    @Nullable
    public static String getValidHexColor(@NotNull String s) {
        if (ColorNameMapper.isNamedColor(s)) {
            return colorToString(colors.get(s));
        } else if (ColorNameMapper.isHexColor(s)) {
            return s;
        }
        return null;
    }

    @NotNull
    public static String findClosestNamedColor(@NotNull Color color) {
        String colorName = "black";
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<String, Color> entry : colors.entrySet()) {
            double distance = colorDistance(color, entry.getValue());
            if (distance < minDistance) {
                minDistance = distance;
                colorName = entry.getKey();
            }
        }
        return colorName;
    }

    @NotNull
    public static String findClosestNamedColor(@NotNull String color) {
        return findClosestNamedColor(colorFromString(color));
    }
}
