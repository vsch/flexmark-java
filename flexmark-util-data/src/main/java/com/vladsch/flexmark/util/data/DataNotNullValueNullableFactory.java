package com.vladsch.flexmark.util.data;

import org.jspecify.annotations.Nullable;

public interface DataNotNullValueNullableFactory<T> extends DataNotNullValueFactory<T> {
    @Override
    T apply(@Nullable DataHolder dataHolder);
}

