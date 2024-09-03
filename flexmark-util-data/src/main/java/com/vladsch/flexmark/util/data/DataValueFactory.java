package com.vladsch.flexmark.util.data;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataValueFactory<T> extends Function<DataHolder, T> {
  @Override
  @Nullable
  T apply(@NotNull DataHolder dataHolder);
}
