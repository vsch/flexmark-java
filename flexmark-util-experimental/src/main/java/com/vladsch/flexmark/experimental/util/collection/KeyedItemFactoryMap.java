package com.vladsch.flexmark.experimental.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class KeyedItemFactoryMap<K, I, P> implements Map<K, Function<P, I>> {
    protected final @NotNull HashMap<K, Function<P, I>> factoryMap;
    protected final @NotNull HashMap<K, I> itemMap;
    protected final @NotNull P param;

    public KeyedItemFactoryMap(@NotNull P param) {
        this(param, 0);
    }

    public KeyedItemFactoryMap(@NotNull P param, int capacity) {
        this.factoryMap = new HashMap<>(capacity);
        this.itemMap = new HashMap<>();
        this.param = param;
    }

    public @Nullable I getItem(@NotNull K key) {
        I item = itemMap.get(key);

        if (item == null) {
            Function<P, I> factory = factoryMap.get(key);
            if (factory == null) {
                throw new IllegalStateException("Factory for key: " + key + " is not defined");
            }
            item = factory.apply(param);
            itemMap.put(key, item);
        }

        return item;
    }

    @Override
    public int size() {return factoryMap.size();}

    @Override
    public boolean isEmpty() {return factoryMap.isEmpty();}

    @Override
    public @Nullable Function<P, I> get(@Nullable Object o) {return factoryMap.get(o);}

    @Override
    public boolean containsKey(@Nullable Object o) {return factoryMap.containsKey(o);}

    @Override
    public @Nullable Function<P, I> put(@NotNull K k, @NotNull Function<P, I> factory) {return factoryMap.put(k, factory);}

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends Function<P, I>> map) {factoryMap.putAll(map);}

    @Override
    public @Nullable Function<P, I> remove(@Nullable Object o) {return factoryMap.remove(o);}

    @Override
    public void clear() {factoryMap.clear();}

    @Override
    public boolean containsValue(Object o) {return factoryMap.containsValue(o);}

    @NotNull
    @Override
    public Set<K> keySet() {return factoryMap.keySet();}

    @NotNull
    @Override
    public Collection<Function<P, I>> values() {return factoryMap.values();}

    @NotNull
    @Override
    public Set<Entry<K, Function<P, I>>> entrySet() {return factoryMap.entrySet();}
}
