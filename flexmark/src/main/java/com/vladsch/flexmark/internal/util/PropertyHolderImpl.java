package com.vladsch.flexmark.internal.util;

import java.util.HashMap;
import java.util.Map;

public class PropertyHolderImpl implements PropertyHolder {
    final protected HashMap<PropertyKey, Object> properties;

    public PropertyHolderImpl() {
        properties = new HashMap<>();
    }

    public PropertyHolderImpl(PropertyHolder other) {
        properties = new HashMap<>();
        properties.putAll(other.getProperties());
    }

    @Override
    public Map<PropertyKey, Object> getProperties() {
        return properties;
    }

    @Override
    public boolean contains(PropertyKey key) {
        return properties.containsKey(key);
    }

    @Override
    public <T> T get(PropertyKey<T> key) {
        if (properties.containsKey(key)) {
            return key.getValue(properties.get(key));
        }
        return null;
    }

    @Override
    public <T> T getOrDefault(PropertyKey<T> key) {
        if (properties.containsKey(key)) {
            return key.getValueOrDefault(properties.get(key));
        }

        return key.getDefaultValue();
    }

    @Override
    public <T> T getOrNew(PropertyKey<T> key) {
        if (properties.containsKey(key)) {
            return key.getValueOrDefault(properties.get(key));
        }

        T value = key.getDefaultValueFactory().value();
        properties.put(key, value);
        return value;
    }
}
