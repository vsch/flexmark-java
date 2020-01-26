package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataNotNullValueNullableFactory<T> extends DataNotNullValueFactory<T> {
    @Override
    @NotNull T apply(@Nullable DataHolder dataHolder);
}

