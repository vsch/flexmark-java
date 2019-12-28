package com.vladsch.flexmark.util.format;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface NumericSuffixPredicate extends Predicate<String> {
    default boolean sortSuffix(@NotNull String suffix) {
        return true;
    }
}
