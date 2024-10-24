package com.vladsch.flexmark.util.data;

import java.util.function.Function;

public interface DataValueFactory<T> extends Function<DataHolder, T> {
  @Override
  T apply(DataHolder dataHolder);
}
