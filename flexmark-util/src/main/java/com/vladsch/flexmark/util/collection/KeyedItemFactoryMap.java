package com.vladsch.flexmark.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class KeyedItemFactoryMap<K, I, P> implements Map<K, Function<P, I>> {
    protected final HashMap<K, Function<P, I>> factoryMap;
    protected final HashMap<K, I> itemMap;
    protected final P param;

    public KeyedItemFactoryMap(P param) {
        this(param, 0);
    }

    public KeyedItemFactoryMap(P param, int capacity) {
        this.factoryMap = new HashMap<>(capacity);
        this.itemMap = new HashMap<>();
        this.param = param;
    }

    public I getItem(K key) {
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
    public Function<P, I> get(Object o) {return factoryMap.get(o);}

    @Override
    public boolean containsKey(Object o) {return factoryMap.containsKey(o);}

    @Override
    public Function<P, I> put(K k, Function<P, I> factory) {return factoryMap.put(k, factory);}

    @Override
    public void putAll(Map<? extends K, ? extends Function<P, I>> map) {factoryMap.putAll(map);}

    @Override
    public Function<P, I> remove(Object o) {return factoryMap.remove(o);}

    @Override
    public void clear() {factoryMap.clear();}

    @Override
    public boolean containsValue(Object o) {return factoryMap.containsValue(o);}

    @Override
    public Set<K> keySet() {return factoryMap.keySet();}

    @Override
    public Collection<Function<P, I>> values() {return factoryMap.values();}

    @Override
    public Set<Entry<K, Function<P, I>>> entrySet() {return factoryMap.entrySet();}
}
