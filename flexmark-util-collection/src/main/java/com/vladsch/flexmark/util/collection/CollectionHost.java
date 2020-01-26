package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.Nullable;

public interface CollectionHost<K> {
    void adding(int index, @Nullable K k, @Nullable Object v);
    @Nullable Object removing(int index, @Nullable K k);
    void clearing();
    void addingNulls(int index); // adding an empty place holder at index

    boolean skipHostUpdate(); // if should not call back host
    int getIteratorModificationCount();  // return version stamp used to detect concurrent modifications
}
