package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.Map;

public interface DataHolder {
    Map<DataKey<?>, Object> getAll();
    Collection<DataKey<?>> getKeys();

    boolean contains(DataKey<?> key);
    <T> T get(DataKey<T> key);

    MutableDataHolder toMutable();

    DataHolder toImmutable();
}
