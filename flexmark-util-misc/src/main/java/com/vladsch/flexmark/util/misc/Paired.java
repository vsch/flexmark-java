package com.vladsch.flexmark.util.misc;

import java.util.Map;

public interface Paired<K, V> extends Map.Entry<K, V> {
  K getFirst();

  V getSecond();

  // Kotlin destructuring
  default K component1() {
    return getFirst();
  }

  default V component2() {
    return getSecond();
  }
}
