package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

public interface MutableDataValueSetter<T> {
    @NotNull MutableDataHolder set(@NotNull MutableDataHolder dataHolder, T value);
}
