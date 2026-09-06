package com.vladsch.flexmark.util.format;


import java.util.function.Predicate;

public interface NumericSuffixPredicate extends Predicate<String> {
    default boolean sortSuffix(String suffix) {
        return true;
    }
}
