package com.vladsch.flexmark.internal.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class CollectingBag<K, S, V> implements Map<K, S> {
    private final HashMap<K, S> myBag;

    public CollectingBag() {
        this(0);
    }

    public CollectingBag(int capacity) {
        this.myBag = new HashMap<K, S>();
    }

    protected abstract S newSet();
    protected abstract boolean addSetItem(S s, V item);
    protected abstract boolean removeSetItem(S s, V item);
    protected abstract boolean containsSetItem(S s, V item);

    public boolean add(K key, V item) {
        S itemSet = myBag.get(key);
        if (itemSet == null) {
            itemSet = newSet();
            myBag.put(key, itemSet);
        }
        return addSetItem(itemSet, item);
    }

    public boolean removeItem(K key, V item) {
        S itemSet = myBag.get(key);
        return itemSet != null && removeSetItem(itemSet, item);
    }

    public boolean containsItem(K key, V item) {
        S itemSet = myBag.get(key);
        return itemSet != null && containsSetItem(itemSet, item);
    }

    @Override
    public int size() {
        return myBag.size();
    }

    @Override
    public boolean isEmpty() {
        return myBag.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return myBag.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return myBag.containsValue(o);
    }

    @Override
    public S get(Object o) {
        return myBag.get(o);
    }

    @Override
    public S put(K k, S vs) {
        return myBag.put(k, vs);
    }

    @Override
    public S remove(Object o) {
        return myBag.remove(o);
    }

    @Override
    public void putAll(Map<? extends K, ? extends S> map) {
        myBag.putAll(map);
    }

    @Override
    public void clear() {
        myBag.clear();
    }

    @Override
    public Set<K> keySet() {
        return myBag.keySet();
    }

    @Override
    public Collection<S> values() {
        return myBag.values();
    }

    @Override
    public Set<Entry<K, S>> entrySet() {
        return myBag.entrySet();
    }
}
