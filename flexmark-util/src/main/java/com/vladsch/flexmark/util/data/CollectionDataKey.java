package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CollectionDataKey<T> extends DataKey<Collection<T>> {
    /**
     * Creates a DataKey with a computed default value dynamically.
     *
     * @param name    See {@link #getName()}.
     * @param factory data value factory for creating a new default value for the key
     */
    public CollectionDataKey(@NotNull String name, @NotNull Collection<T> defaultValue, DataNotNullValueFactory<Collection<T>> factory) {
        super(name, defaultValue, factory);
    }

    @NotNull
    public DataNotNullValueFactory<Collection<T>> getFactory() {
        return super.getFactory();
    }

    @Override
    public String toString() {
        Collection<T> defaultValue = getDefaultValue();
        return "CollectionDataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + getName();
    }
}
