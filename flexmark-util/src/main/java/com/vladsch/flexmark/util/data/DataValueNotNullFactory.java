package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

public interface DataValueNotNullFactory<T> extends DataValueFactory<T> {
    @Override
    T apply(@NotNull DataHolder dataHolder);
}

