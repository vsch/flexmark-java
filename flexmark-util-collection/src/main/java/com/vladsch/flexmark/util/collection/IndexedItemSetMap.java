package com.vladsch.flexmark.util.collection;

import java.util.Map;

interface IndexedItemSetMap<M, S, K> extends Map<M, S> {

  M mapKey(K key);

  S newSet();

  boolean addSetItem(S s, int item);

  boolean removeSetItem(S s, int item);

  boolean containsSetItem(S s, int item);

  boolean addItem(K key, int item);

  boolean removeItem(K key, int item);

  boolean containsItem(K key, int item);
}
