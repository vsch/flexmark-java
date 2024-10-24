package com.vladsch.flexmark.util.data;

public interface MutableDataHolder extends DataHolder {
  @Override
  Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory);

  /**
   * Store the given value for the key
   *
   * @param <T> data type of the data referred by the key
   * @param key data key
   * @param value value to store
   * @return mutable data holder for chained calls
   */
  <T> MutableDataHolder set(DataKey<T> key, T value);

  /**
   * Store the given value for the key
   *
   * @param <T> data type of the data referred by the key
   * @param key data key
   * @param value value to store
   * @return mutable data holder for chained calls
   */
  <T> MutableDataHolder set(NullableDataKey<T> key, T value);

  /**
   * Remove the stored value for the key, used to force to default or to force recompute
   *
   * @param key data key to remove
   * @return mutable data holder for chained calls
   */
  MutableDataHolder remove(DataKeyBase<?> key);

  /**
   * Store the given value for the key
   *
   * @param dataSetter data setter which will set values
   * @return mutable data holder for chained calls
   */
  MutableDataHolder setFrom(MutableDataSetter dataSetter);

  /**
   * Copy all values from one data holder to this data holder
   *
   * @param other data holder from which to copy all values
   * @return mutable data holder for chained calls
   */
  MutableDataHolder setAll(DataHolder other);

  /**
   * Set options in given mutable data holder
   *
   * @param dataHolder data holder where to copy options from this data holder
   * @return dataHolder
   */
  @Override
  MutableDataHolder setIn(MutableDataHolder dataHolder);

  /**
   * clear all options out of the data set
   *
   * @return mutable data holder for chained calls
   */
  MutableDataHolder clear();
}
