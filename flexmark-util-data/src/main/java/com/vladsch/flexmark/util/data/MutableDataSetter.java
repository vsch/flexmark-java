package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

public interface MutableDataSetter {
    @NotNull MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder);
}
