package com.vladsch.flexmark.internal.util;

public class DynamicDefaultKey<T> extends DataKey<T> {
    public DynamicDefaultKey(String name, DataValueFactory<T> factory) {
        super(name, factory);
    }

    @Override
    public T getDefaultValue(DataHolder holder) {
        return getFactory().create(holder);
    }
}
