package com.vladsch.flexmark.internal.util;

public class PropertyKey<T> {
    final private String name;
    final private T defaultValue;

    public PropertyKey(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue(Object value) {
        if (defaultValue.getClass().isInstance(value)) {
            return (T)value;
        }
        return null;
    }

    public T getValueOrDefault(Object value) {
        if (defaultValue.getClass().isInstance(value)) {
            return (T)value;
        }
        return defaultValue;
    }
}
