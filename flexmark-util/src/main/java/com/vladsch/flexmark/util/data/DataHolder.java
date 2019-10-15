package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.Map;

public interface DataHolder {
    Map<DataKey<?>, Object> getAll();
    Collection<DataKey<?>> getKeys();

    /**
     * optimization, returns number of its keys which have Consumer data type
     * @return count of consumer data keys
     */
    int getConsumerDataKeys();

    /**
     * @return collection of keys
     * @deprecated use {@link #getKeys()}
     */
    @Deprecated
    default Collection<DataKey<?>> keySet() {
        return getKeys();
    }

    boolean contains(DataKey<?> key);
    <T> T get(DataKey<T> key);

    MutableDataHolder toMutable();

    DataHolder toImmutable();
}
