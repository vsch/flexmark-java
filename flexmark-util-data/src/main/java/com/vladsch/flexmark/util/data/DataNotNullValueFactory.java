package com.vladsch.flexmark.util.data;

public interface DataNotNullValueFactory<T> extends DataValueFactory<T> {
  @Override
  T apply(DataHolder dataHolder);
}
