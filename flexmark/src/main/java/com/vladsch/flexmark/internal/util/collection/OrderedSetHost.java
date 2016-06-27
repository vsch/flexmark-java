package com.vladsch.flexmark.internal.util.collection;

public interface OrderedSetHost<K> {
    void adding(int index, K k, Object v);
    Object removing(int index, K k);
    void clearing();
}
