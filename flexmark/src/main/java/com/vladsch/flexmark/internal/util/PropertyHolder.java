package com.vladsch.flexmark.internal.util;

import java.util.Map;

public interface PropertyHolder {
    Map<PropertyKey, Object> getProperties();
    boolean contains(PropertyKey key);
    <T> T get(PropertyKey<T> key);
    <T> T getOrDefault(PropertyKey<T> key);
    <T> T getOrNew(PropertyKey<T> key);
}
