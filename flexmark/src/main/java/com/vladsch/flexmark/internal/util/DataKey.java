package com.vladsch.flexmark.internal.util;

public class DataKey<T> {
    final private String name;
    final private DataValueFactory<T> factory;

    final private T defaultValue;

    public DataKey(String name, DataValueFactory<T> factory) {
        this.name = name;
        this.defaultValue = factory.create(null);
        this.factory = factory;
    }

    public DataKey(String name, final T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.factory = (DataHolder options) -> defaultValue;
    }

    public String getName() {
        return name;
    }

    public DataValueFactory<T> getFactory() {
        return factory;
    }

    public T getDefaultValue() {
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
            return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
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
