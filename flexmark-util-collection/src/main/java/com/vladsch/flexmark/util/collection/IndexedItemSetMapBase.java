package com.vladsch.flexmark.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

abstract class IndexedItemSetMapBase<K, S, M> implements IndexedItemSetMap<K, S, M> {
  private final Map<K, S> bag;

  IndexedItemSetMapBase() {
    this.bag = new HashMap<>();
  }

  @Override
  public boolean addItem(M key, int item) {
    K mapKey = mapKey(key);
    S itemSet = bag.get(mapKey);
    if (itemSet == null) {
      itemSet = newSet();
      bag.put(mapKey, itemSet);
    }
    return addSetItem(itemSet, item);
  }

  @Override
  public boolean removeItem(M key, int item) {
    K mapKey = mapKey(key);
    S itemSet = bag.get(mapKey);
    return itemSet != null && removeSetItem(itemSet, item);
  }

  @Override
  public boolean containsItem(M key, int item) {
    K mapKey = mapKey(key);
    S itemSet = bag.get(mapKey);
    return itemSet != null && containsSetItem(itemSet, item);
  }

  @Override
  public int size() {
    return bag.size();
  }

  @Override
  public boolean isEmpty() {
    return bag.isEmpty();
  }

  @Override
  public boolean containsKey(Object o) {
    return bag.containsKey(o);
  }

  @Override
  public boolean containsValue(Object o) {
    return bag.containsValue(o);
  }

  @Override
  public S get(Object o) {
    return bag.get(o);
  }

  @Override
  public S put(K k, S vs) {
    return bag.put(k, vs);
  }

  @Override
  public S remove(Object o) {
    return bag.remove(o);
  }

  @Override
  public void putAll(Map<? extends K, ? extends S> map) {
    bag.putAll(map);
  }

  @Override
  public void clear() {
    bag.clear();
  }

  @Override
  public Set<K> keySet() {
    return bag.keySet();
  }

  @Override
  public Collection<S> values() {
    return bag.values();
  }

  @Override
  public Set<Entry<K, S>> entrySet() {
    return bag.entrySet();
  }
}
