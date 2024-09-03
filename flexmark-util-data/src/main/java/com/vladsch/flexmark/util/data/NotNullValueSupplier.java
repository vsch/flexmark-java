package com.vladsch.flexmark.util.data;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public interface NotNullValueSupplier<T> extends Supplier<T> {
  @NotNull
  @Override
  T get();
}
