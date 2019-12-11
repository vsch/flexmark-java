package com.vladsch.flexmark.experimental.util.collection;

import com.vladsch.flexmark.util.collection.CollectionHost;
import org.jetbrains.annotations.Nullable;

public class NullCollectionHost<K> implements CollectionHost<K> {
    // @formatter:off
    @Override public void adding(int index, @Nullable K k, @Nullable Object v) { }
    @Override public K removing(int index, @Nullable Object o) { return null; }
    @Override public void clearing() { }
    @Override public void addingNulls(int index) { }
    @Override public boolean skipHostUpdate() { return false; }
    @Override public int getIteratorModificationCount() { return 0; }
    // @formatter:on
}
