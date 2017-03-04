package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.ComputableFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemFactoryMap<I, P> implements Map<ComputableFactory<I, P>, I> {
    protected final HashMap<ComputableFactory<I, P>, I> itemMap;
    protected final P param;

    public ItemFactoryMap(P param) {
        this(param, 0);
    }

    public ItemFactoryMap(P param, int capacity) {
        this.itemMap = new HashMap<ComputableFactory<I, P>, I>(capacity);
        this.param = param;
    }

    public I getItem(ComputableFactory<I, P> factory) {
        I item = itemMap.get(factory);
        if (item == null) {
            item = factory.create(param);
            itemMap.put(factory, item);
        }
        return item;
    }

    @Override
    public I get(Object o) {
        if (o instanceof ComputableFactory) {
            return getItem((ComputableFactory<I, P>) o);
        }
        return null;
    }

    @Override
    public int size() {return itemMap.size();}

    @Override
    public boolean isEmpty() {return itemMap.isEmpty();}

    @Override
    public boolean containsKey(Object o) {return itemMap.containsKey(o);}

    @Override
    public I put(ComputableFactory<I, P> factory, I i) {return itemMap.put(factory, i);}

    @Override
    public void putAll(Map<? extends ComputableFactory<I, P>, ? extends I> map) {itemMap.putAll(map);}

    @Override
    public I remove(Object o) {return itemMap.remove(o);}

    @Override
    public void clear() {itemMap.clear();}

    @Override
    public boolean containsValue(Object o) {return itemMap.containsValue(o);}

    @Override
    public Set<ComputableFactory<I, P>> keySet() {return itemMap.keySet();}

    @Override
    public Collection<I> values() {return itemMap.values();}

    @Override
    public Set<Entry<ComputableFactory<I, P>, I>> entrySet() {return itemMap.entrySet();}
}
