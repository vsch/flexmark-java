package com.vladsch.flexmark.internal.util;

import java.util.Map;

public interface DataHolder {
    Map<DataKey, Object> getAll();
    boolean contains(DataKey key);
    <T> T get(DataKey<T> key);
}
