package com.vladsch.flexmark.internal.util;

public class DataKey<T> {
    final private String name;
    final private Factory<T> factory;

    final private T defaultValue;

    public DataKey(String name, Factory<T> factory) {
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

    public Factory<T> getFactory() {
        return factory;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue(Object value) {
        return (T) value;
    }

    @Override
    public String toString() {
        return "DataKey<" + defaultValue.getClass().getName().substring(defaultValue.getClass().getPackage().getName().length() + 1) + "> " + name;
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
        result = 31 * result + defaultValue.hashCode();
        return result;
    }
}
