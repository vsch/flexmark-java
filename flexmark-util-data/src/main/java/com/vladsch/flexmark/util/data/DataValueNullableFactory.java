package com.vladsch.flexmark.util.data;

import org.jspecify.annotations.Nullable;

public interface DataValueNullableFactory<T> extends DataValueFactory<T> {
    @Override
    @Nullable T apply(@Nullable DataHolder dataHolder);
}

