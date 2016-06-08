package com.vladsch.flexmark.internal.util;

import java.util.HashMap;

public interface PropertyHolder {
    HashMap<PropertyKey, Object> getProperties();
    boolean contains(PropertyKey key);
    <T> T getValue(PropertyKey<T> key);
    <T> T getValueOrDefault(PropertyKey<T> key);
    <T> T getValueOrNew(PropertyKey<T> key);
    <T> void setProperty(PropertyKey<T> key, T value);
}
