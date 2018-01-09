package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.collection.DataValueFactory;

public class DataKey<T> {
    private final String name;
    private final DataValueFactory<T> factory;
    private final T defaultValue;

    public DataKey(String name, DataValueFactory<T> factory) {
        this.name = name;
        this.defaultValue = factory.create(null);
        this.factory = factory;
    }

    /**
     * Creates a {@link DataKey} with a dynamic fallback to the value of another key.
     * <p>
     * NOTE: for proper delegation of one key to another you need to use
     * {@link com.vladsch.flexmark.util.collection.DynamicDefaultKey} which does not cache the
     * returned default value but will always delegate to it until this key
     * gets its own value set.
     *
     * @param name       See {@link #getName()}.
     * @param defaultKey The {@link DataKey} to take the default value from.
     */
    private DataKey(String name, final DataKey<? extends T> defaultKey) {
        this(name, new DataValueFactory<T>() {
            @Override
            public T create(DataHolder value) {
                return defaultKey.getFrom(value);
            }
        });
    }

    public DataKey(String name, final T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.factory = new DataValueFactory<T>() {
            @Override
            public T create(DataHolder options) {
                return defaultValue;
            }
        };
    }

    public String getName() {
        return name;
    }

    public DataValueFactory<T> getFactory() {
        return factory;
    }

    public T getDefaultValue(DataHolder holder) {
        return defaultValue;
    }

    public T getValue(Object value) {
        return (T) value;
    }

    public T getFrom(DataHolder holder) {
        return holder == null ? defaultValue : holder.get(this);
    }

    @Override
    public String toString() {
        if (defaultValue != null) {
            return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
        } else {
            T defaultValue = factory.create(null);
            if (defaultValue != null) {
                return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
            } else {
                return "DataKey<unknown> " + name;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + factory.hashCode();
        result = 31 * result + (defaultValue == null ? 0 : defaultValue.hashCode());
        return result;
    }
}
