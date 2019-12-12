package com.vladsch.flexmark.util;

import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class EnumSetUtils {
    public static <T extends Enum<T>> EnumSet<T> toEnumSet(@NotNull Class<T> enumClass, long options) {
        EnumSet<T> optionSet = EnumSet.noneOf(enumClass);
        T[] enumConstants = enumClass.getEnumConstants();

        for (T option : enumConstants) {
            if (option.ordinal() >= 64) break;
            if ((options & (1L << option.ordinal())) != 0) optionSet.add(option);
        }
        return optionSet;
    }

    @SafeVarargs
    public static <T extends Enum<T>> EnumSet<T> toEnumSet(@NotNull Class<T> enumClass, @NotNull T... enumValues) {
        if (enumValues.length == 0) {
            return EnumSet.noneOf(enumClass);
        } else {
            return EnumSet.of(enumValues[0], enumValues);
        }
    }

    public static <T extends Enum<T>> EnumSet<T> fullSet(@NotNull EnumSet<T> enumSet) {
        EnumSet<T> emptySet = enumSet.clone();
        emptySet.clear();
        return EnumSet.complementOf(emptySet);
    }

    public static <T extends Enum<T>> long toLong(@NotNull EnumSet<T> enumSet) {
        // do it the slow way since EnumSet does not expose the internal representation
        long value = 0;
        for (T option : enumSet) {
            if (option.ordinal() >= 64) break;
            value |= 1L << option.ordinal();
        }
        return value;
    }

    public static <T extends Enum<T>> int toInt(@NotNull EnumSet<T> enumSet) {
        return (int) toLong(enumSet);
    }
}
