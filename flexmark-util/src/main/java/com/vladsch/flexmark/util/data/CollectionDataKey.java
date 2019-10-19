package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.Collections;

public class CollectionDataKey<T> extends DataKey<Collection<T>> {
    /**
     * Creates a DataKey with a computed default value dynamically.
     *
     * @param name    See {@link #getName()}.
     * @param factory data value factory for creating a new default value for the key
     */
    public CollectionDataKey(String name, DataValueFactory<Collection<T>> factory) {
        super(name, factory);
    }

    /**
     * Creates a DataKey with a dynamic default value taken from a value of another key
     * <p>
     * does not cache the returned default value but will always delegate to another key until this key
     * gets its own value set.
     *
     * @param name       See {@link #getName()}.
     * @param defaultKey The DataKey to take the default value from at time of construction.
     */
    public CollectionDataKey(String name, CollectionDataKey<T> defaultKey) {
        this(name, defaultKey::getFrom);
    }

    public CollectionDataKey(String name, Collection<T> defaultValue) {
        this(name, options -> defaultValue);
    }

    public DataValueFactory<Collection<T>> getFactory() {
        return super.getFactory();
    }

    public Collection<T> getDefaultValue(DataHolder holder) {
        return super.getDefaultValue(holder);
    }

    public Collection<T> getFrom(DataHolder holder) {
        return super.getFrom(holder);
    }

    @Override
    public String toString() {
        if (super.defaultValue != null) {
            return "CollectionDataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
        } else {
            try {
                Collection<T> defaultValue = factory.apply(null);
            } catch (Throwable e) {
                return "CollectionDataKey(" + super.toString() + " " + name + ") factory " + factory + " generated an exception on apply(null)";
            }

            if (defaultValue != null) {
                return "CollectionDataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
            } else {
                return "CollectionDataKey<unknown> " + name;
            }
        }
    }
}
