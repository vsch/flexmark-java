package com.vladsch.flexmark.util.data;

import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface DataValueFactory<T> extends Function<DataHolder, T> {
    @Override
    @Nullable T apply(DataHolder dataHolder);
}

