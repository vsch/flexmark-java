package com.vladsch.flexmark.util.dependency;

import com.vladsch.flexmark.util.collection.OrderedMap;

public class DependentItemMap<D> extends OrderedMap<Class<?>, DependentItem<D>> {
  public DependentItemMap() {}

  public DependentItemMap(int capacity) {
    super(capacity);
  }
}
