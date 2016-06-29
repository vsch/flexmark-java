package com.vladsch.flexmark.internal.util.collection;

public interface MutableDataHolder extends DataHolder {
    /**
     * Get the given key, if it does not exist then use the key's factory to create a new value and put it into the collection
     * so that the following get of the same key will find a value
     *
     * @param key data key
     * @return return stored value or newly created value
     */
    @Override
    <T> T get(DataKey<T> key);

    /**
     * Store the given value for the key
     *
     * @param key   data key
     * @param value value to store
     * @return mutable data holder for chained calls
     */
    <T> MutableDataHolder set(DataKey<T> key, T value);

    /**
     * Copy all values from one data holder to this data holder
     *
     * @param other data holder from which to copy all values
     */
    void setAll(DataHolder other);

    /**
     * @param key
     * @param factory
     * @param <T>
     * @return
     */
    <T> T getOrCompute(DataKey<T> key, DataValueFactory<T> factory);
}
