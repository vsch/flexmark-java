package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        this.myBag = new HashMap<>();
    }

    @Override
    public abstract @NotNull K mapKey(@NotNull M key);
    @Override
    public abstract @NotNull S newSet();
    @Override
    public abstract boolean addSetItem(@NotNull S s, int item);
    @Override
    public abstract boolean removeSetItem(@NotNull S s, int item);
    @Override
    public abstract boolean containsSetItem(@NotNull S s, int item);

    @Override
    public boolean addItem(@NotNull M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = myBag.get(mapKey);
        if (itemSet == null) {
            itemSet = newSet();
            myBag.put(mapKey, itemSet);
        }
        return addSetItem(itemSet, item);
    }

    @Override
    public boolean removeItem(@NotNull M key, int item) {
        K mapKey = mapKey(key);
        S itemSet = myBag.get(mapKey);
        return itemSet != null && removeSetItem(itemSet, item);
    }

    @Override
    public boolean containsItem(@NotNull M key, int item) {
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
    public boolean containsKey(@Nullable Object o) {
        return myBag.containsKey(o);
    }

    @Override
    public boolean containsValue(@Nullable Object o) {
        return myBag.containsValue(o);
    }

    @Override
    public @Nullable S get(@Nullable Object o) {
        return myBag.get(o);
    }

    @Override
    public @Nullable S put(@NotNull K k, @NotNull S vs) {
        return myBag.put(k, vs);
    }

    @Override
    public @Nullable S remove(@Nullable Object o) {
        return myBag.remove(o);
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends S> map) {
        myBag.putAll(map);
    }

    @Override
    public void clear() {
        myBag.clear();
    }

    @Override
    public @NotNull Set<K> keySet() {
        return myBag.keySet();
    }

    @Override
    public @NotNull Collection<S> values() {
        return myBag.values();
    }

    @Override
    public @NotNull Set<Entry<K, S>> entrySet() {
        return myBag.entrySet();
    }
}
