package com.vladsch.flexmark.internal.util;

public interface MutablePropertyHolder extends PropertyHolder {
    <T> MutablePropertyHolder set(PropertyKey<T> key, T value);

    PropertyHolder getReadOnlyCopy();
}
