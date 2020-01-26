package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

public interface DataNotNullValueFactory<T> extends DataValueFactory<T> {
    @Override
    @NotNull T apply(@NotNull DataHolder dataHolder);
}

