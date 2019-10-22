package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public interface DataHolder {
    @NotNull Map<DataKey<?>, Object> getAll();
    @NotNull Collection<DataKey<?>> getKeys();

    boolean contains(@NotNull DataKey<?> key);
    @Nullable <T> T get(@NotNull DataKey<T> key);

    @NotNull MutableDataHolder toMutable();
    @NotNull DataHolder toImmutable();

    @NotNull
    default DataSet toDataSet() {
        return this instanceof DataSet ? (DataSet) this : this instanceof MutableDataHolder ? new MutableDataSet(this) : new DataSet(this);
    }
}
