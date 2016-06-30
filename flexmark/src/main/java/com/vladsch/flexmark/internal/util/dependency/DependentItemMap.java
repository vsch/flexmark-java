package com.vladsch.flexmark.internal.util.dependency;

import com.vladsch.flexmark.internal.util.collection.CollectionHost;
import com.vladsch.flexmark.internal.util.collection.OrderedMap;

public class DependentItemMap<D> extends OrderedMap<Class<? extends D>, DependentItem<D>> {
    public DependentItemMap() {
    }

    public DependentItemMap(int capacity) {
        super(capacity);
    }

    public DependentItemMap(CollectionHost<Class<? extends D>> host) {
        super(host);
    }

    public DependentItemMap(int capacity, CollectionHost<Class<? extends D>> host) {
        super(capacity, host);
    }
}
