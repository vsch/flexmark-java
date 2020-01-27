package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public interface DataHolder extends MutableDataSetter {
    DataHolder NULL = new DataSet();

    @NotNull Map<? extends DataKeyBase<?>, Object> getAll();
    @NotNull Collection<? extends DataKeyBase<?>> getKeys();

    boolean contains(@NotNull DataKeyBase<?> key);

    /**
     * @param key data key
     * @param <T> Type returned by key
     * @return Use key.get(dataHolder) instead
     */
    @Deprecated
    @Nullable
    default <T> T get(@NotNull DataKey<T> key) {
        return key.get(this);
    }

    @Override
    default @NotNull MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        return dataHolder.setAll(this);
    }

    /**
     * Get key if it exists or compute using supplier
     * <p>
     * Method used by DataKey classes to access data.
     * <p>
     * NOTE: MutableDataHolders will compute an absent key and add it to its dataSet.
     * DataHolders will return computed value but not change contained dataSet
     * because they are immutable. So value will be computed every time it is requested.
     *
     * @param key     data key
     * @param factory factory taking this data holder and computing/providing default value
     * @return object value for the key
     */
    Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory);

    @NotNull MutableDataHolder toMutable();
    @NotNull DataHolder toImmutable();

    @NotNull
    default DataSet toDataSet() {
        return this instanceof DataSet ? (DataSet) this : this instanceof MutableDataHolder ? new MutableDataSet(this) : new DataSet(this);
    }
}
