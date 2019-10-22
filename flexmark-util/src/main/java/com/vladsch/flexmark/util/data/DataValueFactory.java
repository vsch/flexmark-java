package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface DataValueFactory<T> extends Function<DataHolder, T> {
    @Override
    @Nullable T apply(@Nullable DataHolder dataHolder);
}

