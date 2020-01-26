package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface NotNullValueSupplier<T> extends Supplier<T> {
    @NotNull
    @Override
    T get();
}

