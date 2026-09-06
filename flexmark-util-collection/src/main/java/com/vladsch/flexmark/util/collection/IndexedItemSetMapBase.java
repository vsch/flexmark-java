package com.vladsch.flexmark.util.collection;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class IndexedItemSetMapBase<K, S, M> implements IndexedItemSetMap<K, S, M> {
    protected final HashMap<K, S> bag;

    public IndexedItemSetMapBase() {
        this(0);
    }

    public IndexedItemSetMapBase(int capacity) {
        this.bag = new HashMap<>();
    }

    @Override
    public abstract K mapKey(M key);
    @Override
    public abstract S newSet();
    @Override
    public abstract boolean addSetItem(S s, int item);
    @Override
    public abstract boolean removeSetItem(S s, int item);
    @Override
    public abstract boolean containsSetItem(S s, int item);

    @Override
    public boolean addItem(M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = bag.get(mapKey);
        if (itemSet == null) {
            itemSet = newSet();
            bag.put(mapKey, itemSet);
        }
        return addSetItem(itemSet, item);
    }

    @Override
    public boolean removeItem(M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = bag.get(mapKey);
        return itemSet != null && removeSetItem(itemSet, item);
    }

    @Override
    public boolean containsItem(M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = bag.get(mapKey);
        return itemSet != null && containsSetItem(itemSet, item);
    }

    @Override
    public int size() {
        return bag.size();
    }

    @Override
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object o) {
        return bag.containsKey(o);
    }

    @Override
    public boolean containsValue(@Nullable Object o) {
        return bag.containsValue(o);
    }

    @Override
    public @Nullable S get(@Nullable Object o) {
        return bag.get(o);
    }

    @Override
    public @Nullable S put(K k, S vs) {
        return bag.put(k, vs);
    }

    @Override
    public @Nullable S remove(@Nullable Object o) {
        return bag.remove(o);
    }

    @Override
    public void putAll(Map<? extends K, ? extends S> map) {
        bag.putAll(map);
    }

    @Override
    public void clear() {
        bag.clear();
    }

    @Override
    public Set<K> keySet() {
        return bag.keySet();
    }

    @Override
    public Collection<S> values() {
        return bag.values();
    }

    @Override
    public Set<Entry<K, S>> entrySet() {
        return bag.entrySet();
    }
}
