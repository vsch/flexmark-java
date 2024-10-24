package com.vladsch.flexmark.util.collection;

public interface CollectionHost<K> {
  void adding(int index, K k, Object v);

  Object removing(int index, K k);

  void clearing();

  void addingNulls(int index); // adding an empty place holder at index

  boolean skipHostUpdate(); // if should not call back host

  int getIteratorModificationCount(); // return version stamp used to detect concurrent
  // modifications
}
