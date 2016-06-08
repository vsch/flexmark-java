package com.vladsch.flexmark.internal.util;

public class PropertyKey<T> {
    final private String name;
    final private ValueFactory<T> defaultValueFactory;
    final private T defaultValue;

    public PropertyKey(String name, ValueFactory<T> defaultValueFactory) {
        this.name = name;
        this.defaultValueFactory = defaultValueFactory;
        this.defaultValue = defaultValueFactory.value();
    }

    public PropertyKey(String name, final T defaultValue, ValueFactory<T> defaultValueFactory) {
        this.name = name;
        this.defaultValueFactory = defaultValueFactory;
        this.defaultValue = defaultValue;
    }

    public PropertyKey(String name, final T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.defaultValueFactory = new ValueFactory<T>() {
            @Override
            public T value() {
                return defaultValue;
            }
        };
    }

    public String getName() {
        return name;
    }

    public ValueFactory<T> getDefaultValueFactory() {
        return defaultValueFactory;
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
        return defaultValueFactory.value();
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}
