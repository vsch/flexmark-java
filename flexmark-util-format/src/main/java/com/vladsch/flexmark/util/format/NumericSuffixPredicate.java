package com.vladsch.flexmark.util.format;

import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public interface NumericSuffixPredicate extends Predicate<String> {
    default boolean sortSuffix(@NotNull String suffix) {
        return true;
    }
}
