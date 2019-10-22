package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface DataNotNullValueFactory<T> extends DataValueFactory<T> {
    @Override
    @NotNull T apply(@NotNull DataHolder dataHolder);
}

