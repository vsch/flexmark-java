package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ItemFactoryMap<I, P> implements Map<Function<P, I>, I> {
    protected final HashMap<Function<P, I>, I> itemMap;
    protected final @NotNull P param;

    public ItemFactoryMap(@NotNull P param) {
        this(param, 0);
    }

    public ItemFactoryMap(@NotNull P param, int capacity) {
        this.itemMap = new HashMap<>(capacity);
        this.param = param;
    }

    public I getItem(Function<P, I> factory) {
        I item = itemMap.get(factory);
        if (item == null) {
            item = factory.apply(param);
            itemMap.put(factory, item);
        }
        return item;
    }

    @Override
    public @Nullable I get(@Nullable Object o) {
        if (o instanceof Function) {
            //noinspection unchecked
            return getItem((Function<P, I>) o);
        }
        return null;
    }

    @Override
    public int size() {return itemMap.size();}

    @Override
    public boolean isEmpty() {return itemMap.isEmpty();}

    @Override
    public boolean containsKey(@Nullable Object o) {return itemMap.containsKey(o);}

    @Override
    public @Nullable I put(@NotNull Function<P, I> factory, @NotNull I i) {return itemMap.put(factory, i);}

    @Override
    public void putAll(@NotNull Map<? extends Function<P, I>, ? extends I> map) {itemMap.putAll(map);}

    @Override
    public @Nullable I remove(@Nullable Object o) {return itemMap.remove(o);}

    @Override
    public void clear() {itemMap.clear();}

    @Override
    public boolean containsValue(@Nullable Object o) {return itemMap.containsValue(o);}

    @Override
    public @NotNull Set<Function<P, I>> keySet() {return itemMap.keySet();}

    @Override
    public @NotNull Collection<I> values() {return itemMap.values();}

    @Override
    public @NotNull Set<Entry<Function<P, I>, I>> entrySet() {return itemMap.entrySet();}
}
