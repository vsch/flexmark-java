package com.vladsch.flexmark.internal.util.collection;

public interface OrderedSetHost<K> {
    void adding(int index, K k, Object v);
    Object removing(int index, K k);
    void clearing();
    
    // addingKey an empty place holder at index
    void addingNull(int index);
    
    // if already in host update through another hosted/related set
    boolean hostInUpdate();
}
