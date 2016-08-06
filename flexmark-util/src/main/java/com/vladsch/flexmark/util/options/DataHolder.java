package com.vladsch.flexmark.util.options;

import java.util.Collection;
import java.util.Map;

public interface DataHolder {
    Map<DataKey, Object> getAll();
    Collection<DataKey> keySet();
    boolean contains(DataKey key);
    <T> T get(DataKey<T> key);
    
    MutableDataHolder toMutable();
    
    DataHolder toImmutable();
}
