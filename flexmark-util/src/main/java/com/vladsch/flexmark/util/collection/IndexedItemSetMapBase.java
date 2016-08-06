package com.vladsch.flexmark.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class IndexedItemSetMapBase<K, S, M> implements IndexedItemSetMap<K, S, M> {
    protected final HashMap<K, S> myBag;

    public IndexedItemSetMapBase() {
        this(0);
    }

    public IndexedItemSetMapBase(int capacity) {
        this.myBag = new HashMap<K, S>();
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
        S itemSet = myBag.get(mapKey);
        if (itemSet == null) {
            itemSet = newSet();
            myBag.put(mapKey, itemSet);
        }
        return addSetItem(itemSet, item);
    }

    @Override
    public boolean removeItem(M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = myBag.get(mapKey);
        return itemSet != null && removeSetItem(itemSet, item);
    }

    @Override
    public boolean containsItem(M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = myBag.get(mapKey);
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
