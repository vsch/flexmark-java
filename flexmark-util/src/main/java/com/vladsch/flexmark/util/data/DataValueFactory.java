package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface DataValueFactory<T> extends Function<DataHolder, T> {
    @Override
    @Nullable T apply(@NotNull DataHolder dataHolder);
}

