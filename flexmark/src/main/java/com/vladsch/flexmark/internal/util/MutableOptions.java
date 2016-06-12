package com.vladsch.flexmark.internal.util;

public class MutableOptions extends Options implements MutablePropertyHolder {
    public MutableOptions() {
    }

    public MutableOptions(PropertyHolder other) {
        super(other);
    }

    @Override
    public <T> MutableOptions set(PropertyKey<T> key, T value) {
        properties.put(key, value);
        return this;
    }

    @Override
    public Options getReadOnlyCopy() {
        return new Options(this);
    }
}
