package com.vladsch.flexmark.internal.util;

import java.util.HashMap;

public class PropertyHolderImpl implements PropertyHolder {
    final private HashMap<PropertyKey, Object> properties;

    public PropertyHolderImpl() {
        properties = new HashMap<>();
    }

    @Override
    public HashMap<PropertyKey, Object> getProperties() {
        return properties;
    }

    @Override
    public boolean contains(PropertyKey key) {
        return properties.containsKey(key);
    }

    @Override
    public <T> T getValue(PropertyKey<T> key) {
        if (properties.containsKey(key)) {
            return key.getValue(properties.get(key));
        }
        return null;
    }

    @Override
    public <T> T getValueOrDefault(PropertyKey<T> key) {
        if (properties.containsKey(key)) {
            return key.getValueOrDefault(properties.get(key));
        }

        return key.getDefaultValue();
    }

    @Override
    public <T> void setProperty(PropertyKey<T> key, T value) {
        properties.put(key, value);
    }
}
