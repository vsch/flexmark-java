package com.vladsch.flexmark.util.collection.iteration;

public interface Indexed<E> {
  E get(int index);

  void set(int index, E item);

  void removeAt(int index);

  int size();

  int modificationCount();
}
