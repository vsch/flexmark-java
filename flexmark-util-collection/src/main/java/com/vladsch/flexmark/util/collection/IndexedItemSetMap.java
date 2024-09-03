package com.vladsch.flexmark.util.collection;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface IndexedItemSetMap<M, S, K> extends Map<M, S> {
    @NotNull M mapKey(@NotNull K key);

    @NotNull S newSet();
    boolean addSetItem(@NotNull S s, int item);
    boolean removeSetItem(@NotNull S s, int item);
    boolean containsSetItem(@NotNull S s, int item);

    boolean addItem(@NotNull K key, int item);
    boolean removeItem(@NotNull K key, int item);
    boolean containsItem(@NotNull K key, int item);
}
