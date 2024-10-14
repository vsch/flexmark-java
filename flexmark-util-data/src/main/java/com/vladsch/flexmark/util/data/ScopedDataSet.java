package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScopedDataSet extends DataSet {
  private final DataHolder parent;

  public ScopedDataSet(@Nullable DataHolder parent, @Nullable DataHolder other) {
    super(other);
    this.parent = parent;
  }

  public DataHolder getParent() {
    return parent;
  }

  @Override
  public @NotNull Map<? extends DataKeyBase<?>, Object> getAll() {
    if (parent != null) {
      Map<DataKeyBase<?>, Object> all = new HashMap<>(parent.getAll());
      all.putAll(super.getAll());
      return all;
    }

    return super.getAll();
  }

  @Override
  public @NotNull Collection<? extends DataKeyBase<?>> getKeys() {
    if (parent != null) {
      Set<DataKeyBase<?>> all = new HashSet<>(parent.getKeys());
      all.addAll(super.getKeys());
      return all;
    }

    return super.getKeys();
  }

  @Override
  public @NotNull MutableDataSet toMutable() {
    MutableDataSet mutableDataSet = new MutableDataSet();
    mutableDataSet.dataSet.putAll(super.getAll());
    return parent != null ? new MutableScopedDataSet(parent, mutableDataSet) : mutableDataSet;
  }

  @Override
  public boolean contains(@NotNull DataKeyBase<?> key) {
    return super.contains(key) || (parent != null && parent.contains(key));
  }

  @Override
  public @Nullable Object getOrCompute(
      @NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
    if (parent == null || super.contains(key) || !parent.contains(key)) {
      return super.getOrCompute(key, factory);
    }

    return parent.getOrCompute(key, factory);
  }
}
