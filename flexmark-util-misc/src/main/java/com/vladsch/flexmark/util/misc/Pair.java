package com.vladsch.flexmark.util.misc;

import java.util.Map;
import java.util.Objects;

public final class Pair<K, V> implements Paired<K, V> {
  private final K first;
  private final V second;

  public Pair(K first, V second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public K getFirst() {
    return first;
  }

  @Override
  public V getSecond() {
    return second;
  }

  @Override
  public K getKey() {
    return first;
  }

  @Override
  public V getValue() {
    return second;
  }

  @Override
  public V setValue(V value) {
    throw new IllegalStateException("setValue not supported");
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    out.append('(');

    if (first == null) {
      out.append("null");
    } else {
      out.append(first);
    }

    out.append(", ");

    if (second == null) {
      out.append("null");
    } else {
      out.append(second);
    }

    out.append(')');
    return out.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(object instanceof Map.Entry<?, ?>)) {
      return false;
    }

    Map.Entry<?, ?> pair = (Map.Entry<?, ?>) object;

    if (!Objects.equals(first, pair.getKey())) {
      return false;
    }
    return Objects.equals(second, pair.getValue());
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (second != null ? second.hashCode() : 0);
    return result;
  }
}
