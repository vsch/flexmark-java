package com.vladsch.flexmark.util.collection;

import java.util.Map;
import java.util.Objects;

final class MapEntry<K, V> implements Map.Entry<K, V> {
  private final K key;
  private final V value;

  MapEntry(K key, V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public K getKey() {
    return key;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public V setValue(V v) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MapEntry<?, ?> entry = (MapEntry<?, ?>) o;

    if (!Objects.equals(key, entry.key)) {
      return false;
    }
    return Objects.equals(value, entry.value);
  }

  @Override
  public int hashCode() {
    int result = key.hashCode();
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}
