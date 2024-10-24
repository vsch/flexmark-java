package com.vladsch.flexmark.util.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MutableScopedDataSet extends MutableDataSet {
  private final DataHolder parent;

  MutableScopedDataSet(DataHolder parent, MutableDataHolder other) {
    super(other);
    this.parent = parent;
  }

  public DataHolder getParent() {
    return parent;
  }

  @Override
  public Map<? extends DataKeyBase<?>, Object> getAll() {
    if (parent != null) {
      Map<DataKeyBase<?>, Object> all = new HashMap<>(super.getAll());
      for (DataKeyBase<?> key : parent.getKeys()) {
        if (!contains(key)) {
          all.put(key, key.get(parent));
        }
      }

      return all;
    }

    return super.getAll();
  }

  @Override
  public Collection<? extends DataKeyBase<?>> getKeys() {
    if (parent != null) {
      List<DataKeyBase<?>> all = new ArrayList<>(super.getKeys());
      for (DataKeyBase<?> key : parent.getKeys()) {
        if (!contains(key)) {
          all.add(key);
        }
      }

      return all;
    }

    return super.getKeys();
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
