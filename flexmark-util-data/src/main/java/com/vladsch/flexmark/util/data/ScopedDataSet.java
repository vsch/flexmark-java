package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScopedDataSet extends DataSet {
  private final DataHolder parent;

  public ScopedDataSet(DataHolder parent, DataHolder other) {
    super(other);
    this.parent = parent;
  }

  public DataHolder getParent() {
    return parent;
  }

  @Override
  public Map<? extends DataKeyBase<?>, Object> getAll() {
    if (parent != null) {
      Map<DataKeyBase<?>, Object> all = new HashMap<>(parent.getAll());
      all.putAll(super.getAll());
      return all;
    }

    return super.getAll();
  }

  @Override
  public Collection<? extends DataKeyBase<?>> getKeys() {
    if (parent != null) {
      Set<DataKeyBase<?>> all = new HashSet<>(parent.getKeys());
      all.addAll(super.getKeys());
      return all;
    }

    return super.getKeys();
  }

  @Override
  public MutableDataSet toMutable() {
    MutableDataSet mutableDataSet = new MutableDataSet();
    mutableDataSet.dataSet.putAll(super.getAll());
    return parent != null ? new MutableScopedDataSet(parent, mutableDataSet) : mutableDataSet;
  }

  @Override
  public boolean contains(DataKeyBase<?> key) {
    return super.contains(key) || (parent != null && parent.contains(key));
  }

  @Override
  public Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory) {
    if (parent == null || super.contains(key) || !parent.contains(key)) {
      return super.getOrCompute(key, factory);
    }

    return parent.getOrCompute(key, factory);
  }
}
