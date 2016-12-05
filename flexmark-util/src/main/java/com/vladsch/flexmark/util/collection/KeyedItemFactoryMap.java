package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.ComputableFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyedItemFactoryMap<K, I, P> implements Map<K, ComputableFactory<I, P>> {
    protected final HashMap<K, ComputableFactory<I, P>> factoryMap;
    protected final HashMap<K, I> itemMap;
    protected final P param;

    public KeyedItemFactoryMap(P param) {
        this(param, 0);
    }

    public KeyedItemFactoryMap(P param, int capacity) {
        this.factoryMap = new HashMap<K, ComputableFactory<I, P>>(capacity);
        this.itemMap = new HashMap<K, I>();
        this.param = param;
    }

    public I getItem(K key) {
        I item = itemMap.get(key);

        if (item == null) {
            ComputableFactory<I, P> factory = factoryMap.get(key);
            if (factory == null) {
                throw new IllegalStateException("Factory for key: " + key + " is not defined");
            }
            item = factory.create(param);
            itemMap.put(key, item);
        }

        return item;
    }

    @Override
    public int size() {return factoryMap.size();}

    @Override
    public boolean isEmpty() {return factoryMap.isEmpty();}

    @Override
    public ComputableFactory<I, P> get(Object o) {return factoryMap.get(o);}

    @Override
    public boolean containsKey(Object o) {return factoryMap.containsKey(o);}

    @Override
    public ComputableFactory<I, P> put(K k, ComputableFactory<I, P> factory) {return factoryMap.put(k, factory);}

    @Override
    public void putAll(Map<? extends K, ? extends ComputableFactory<I, P>> map) {factoryMap.putAll(map);}

    @Override
    public ComputableFactory<I, P> remove(Object o) {return factoryMap.remove(o);}

    @Override
    public void clear() {factoryMap.clear();}

    @Override
    public boolean containsValue(Object o) {return factoryMap.containsValue(o);}

    @Override
    public Set<K> keySet() {return factoryMap.keySet();}

    @Override
    public Collection<ComputableFactory<I, P>> values() {return factoryMap.values();}

    @Override
    public Set<Entry<K, ComputableFactory<I, P>>> entrySet() {return factoryMap.entrySet();}
}
