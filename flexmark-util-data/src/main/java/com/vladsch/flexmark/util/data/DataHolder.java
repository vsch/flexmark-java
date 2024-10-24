package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.Map;

public interface DataHolder extends MutableDataSetter {

  Map<? extends DataKeyBase<?>, Object> getAll();

  Collection<? extends DataKeyBase<?>> getKeys();

  boolean contains(DataKeyBase<?> key);

  @Override
  default MutableDataHolder setIn(MutableDataHolder dataHolder) {
    return dataHolder.setAll(this);
  }

  /**
   * Get key if it exists or compute using supplier
   *
   * <p>Method used by DataKey classes to access data.
   *
   * <p>NOTE: MutableDataHolders will compute an absent key and add it to its dataSet. DataHolders
   * will return computed value but not change contained dataSet because they are immutable. So
   * value will be computed every time it is requested.
   *
   * @param key data key
   * @param factory factory taking this data holder and computing/providing default value
   * @return object value for the key
   */
  Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory);

  MutableDataHolder toMutable();

  DataHolder toImmutable();

  default DataSet toDataSet() {
    return this instanceof DataSet
        ? (DataSet) this
        : this instanceof MutableDataHolder ? new MutableDataSet(this) : new DataSet(this);
  }
}
