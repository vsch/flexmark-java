package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.Map;

public interface DataHolder {
    Map<DataKey<?>, Object> getAll();
    Collection<DataKey<?>> getKeys();

    boolean contains(DataKey<?> key);
    <T> T get(DataKey<T> key);

    MutableDataSet toMutable();
    DataSet toImmutable();

    default DataSet toDataSet() {
        return this instanceof DataSet ? (DataSet) this : this instanceof MutableDataHolder ? toMutable() : toImmutable();
    }
}
