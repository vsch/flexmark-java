package com.vladsch.flexmark.util.data;

public class DataKey<T> {
    private final String name;
    private final DataValueFactory<T> factory;
    private final T defaultValue;

    /**
     * Creates a DataKey with a computed default value dynamically.
     *
     * @param name    See {@link #getName()}.
     * @param factory data value factory for creating a new default value for the key
     */
    public DataKey(String name, DataValueFactory<T> factory) {
        this.name = name;
        this.defaultValue = factory.apply(null);
        this.factory = factory;
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
    public DataKey(String name, DataKey<? extends T> defaultKey) {
        this(name, defaultKey::getFrom);
    }

    public DataKey(String name, T defaultValue) {
        this(name, options -> defaultValue);
    }

    public String getName() {
        return name;
    }

    public DataValueFactory<T> getFactory() {
        return factory;
    }

    public T getDefaultValue(DataHolder holder) {
        return holder == null ? defaultValue : factory.apply(holder);
    }

    public T getFrom(DataHolder holder) {
        return holder == null ? defaultValue : holder.get(this);
    }

    @Override
    public String toString() {
        if (defaultValue != null) {
            return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
        } else {
            T defaultValue = factory.apply(null);
            if (defaultValue != null) {
                return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
            } else {
                return "DataKey<unknown> " + name;
            }
        }
    }

    /**
     * Compare only by address. Every key instance is unique
     * @param o other
     * @return true if equal
     */
    @Override
    final public boolean equals(Object o) {
        return this == o;
    }

    @Override
    final public int hashCode() {
        return super.hashCode();
    }
}
