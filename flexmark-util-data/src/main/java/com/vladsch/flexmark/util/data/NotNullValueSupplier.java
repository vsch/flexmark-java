package com.vladsch.flexmark.util.data;

import java.util.function.Supplier;

public interface NotNullValueSupplier<T> extends Supplier<T> {

  @Override
  T get();
}
