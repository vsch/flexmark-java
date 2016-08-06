package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

public class DynamicDefaultKey<T> extends DataKey<T> {
    public DynamicDefaultKey(String name, DataValueFactory<T> factory) {
        super(name, factory);
    }

    @Override
    public T getDefaultValue(DataHolder holder) {
        return getFactory().create(holder);
    }
}
