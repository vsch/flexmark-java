package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Data key that returns a new value for every getDefaultValue() call instead of caching the value
 * <p>
 * This has the effect of this key returning the value of another key until this key receives its own value.
 */
public class DynamicDefaultKey<T> extends DataKey<T> {
    public DynamicDefaultKey(String name, DataValueFactory<T> factory) {
        super(name, factory);
    }

    /**
     * Creates a {@link DataKey} with a dynamic fallback to the value of another key.
     *
     * @param name       See {@link #getName()}.
     * @param defaultKey The {@link DataKey} to take the default value from.
     */
    public DynamicDefaultKey(String name, final DataKey<? extends T> defaultKey) {
        this(name, new DataValueFactory<T>() {
            @Override
            public T create(DataHolder value) {
                return defaultKey.getFrom(value);
            }
        });
    }

    @Override
    public T getDefaultValue(DataHolder holder) {
        return getFactory().create(holder);
    }
}
