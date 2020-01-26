package com.vladsch.flexmark.util.dependency;

import com.vladsch.flexmark.util.collection.CollectionHost;
import com.vladsch.flexmark.util.collection.OrderedMap;

public class DependentItemMap<D> extends OrderedMap<Class<?>, DependentItem<D>> {
    public DependentItemMap() {
    }

    public DependentItemMap(int capacity) {
        super(capacity);
    }

    public DependentItemMap(CollectionHost<Class<?>> host) {
        super(host);
    }

    public DependentItemMap(int capacity, CollectionHost<Class<?>> host) {
        super(capacity, host);
    }
}
