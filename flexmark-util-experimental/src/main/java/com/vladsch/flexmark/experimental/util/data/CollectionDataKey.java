package com.vladsch.flexmark.experimental.util.data;

import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataNotNullValueFactory;

import java.util.Collection;

public class CollectionDataKey<T> extends DataKey<Collection<T>> {
    /**
     * Creates a DataKey with a computed default value dynamically.
     *
     * @param name    See {@link #getName()}.
     * @param defaultValue default value for collection key
     * @param factory data value factory for creating a new default value for the key
     */
    public CollectionDataKey(String name, Collection<T> defaultValue, DataNotNullValueFactory<Collection<T>> factory) {
        super(name, defaultValue, factory);
    }

    public DataNotNullValueFactory<Collection<T>> getFactory() {
        return super.getFactory();
    }

    @Override
    public String toString() {
        Collection<T> defaultValue = getDefaultValue();
        return "CollectionDataKey<" + defaultValue.getClass().getSimpleName() + "> " + getName();
    }
}
