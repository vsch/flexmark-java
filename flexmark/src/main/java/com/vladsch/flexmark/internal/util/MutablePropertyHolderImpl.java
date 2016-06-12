package com.vladsch.flexmark.internal.util;

public class MutablePropertyHolderImpl extends PropertyHolderImpl implements MutablePropertyHolder {
    public MutablePropertyHolderImpl() {
        super();
    }

    @Override
    public <T> MutablePropertyHolder set(PropertyKey<T> key, T value) {
        properties.put(key, value);
        return this;
    }

    @Override
    public PropertyHolder getReadOnlyCopy() {
        return new PropertyHolderImpl(this);
    }
}
