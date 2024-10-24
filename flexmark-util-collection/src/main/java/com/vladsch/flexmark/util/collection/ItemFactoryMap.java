package com.vladsch.flexmark.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ItemFactoryMap<I, P> implements Map<Function<P, I>, I> {
  private final Map<Function<P, I>, I> itemMap;
  private final P param;

  public ItemFactoryMap(P param) {
    this(param, 0);
  }

  private ItemFactoryMap(P param, int capacity) {
    this.itemMap = new HashMap<>(capacity);
    this.param = param;
  }

  public I getItem(Function<P, I> factory) {
    I item = itemMap.get(factory);
    if (item == null) {
      item = factory.apply(param);
      itemMap.put(factory, item);
    }
    return item;
  }

  @Override
  public I get(Object o) {
    if (o instanceof Function) {
      return getItem((Function<P, I>) o);
    }
    return null;
  }

  @Override
  public int size() {
    return itemMap.size();
  }

  @Override
  public boolean isEmpty() {
    return itemMap.isEmpty();
  }

  @Override
  public boolean containsKey(Object o) {
    return itemMap.containsKey(o);
  }

  @Override
  public I put(Function<P, I> factory, I i) {
    return itemMap.put(factory, i);
  }

  @Override
  public void putAll(Map<? extends Function<P, I>, ? extends I> map) {
    itemMap.putAll(map);
  }

  @Override
  public I remove(Object o) {
    return itemMap.remove(o);
  }

  @Override
  public void clear() {
    itemMap.clear();
  }

  @Override
  public boolean containsValue(Object o) {
    return itemMap.containsValue(o);
  }

  @Override
  public Set<Function<P, I>> keySet() {
    return itemMap.keySet();
  }

  @Override
  public Collection<I> values() {
    return itemMap.values();
  }

  @Override
  public Set<Entry<Function<P, I>, I>> entrySet() {
    return itemMap.entrySet();
  }
}
