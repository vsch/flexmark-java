package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface IndexedItemSetMap<M, S, K> extends Map<M, S> {
    @NotNull M mapKey(@NotNull K key);

    @NotNull S newSet();
    boolean addSetItem(@NotNull S s, int item);
    boolean removeSetItem(@NotNull S s, int item);
    boolean containsSetItem(@NotNull S s, int item);

    @SuppressWarnings("UnusedReturnValue")
    boolean addItem(@NotNull K key, int item);
    @SuppressWarnings("UnusedReturnValue")
    boolean removeItem(@NotNull K key, int item);
    boolean containsItem(@NotNull K key, int item);
}
