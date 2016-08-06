package com.vladsch.flexmark.util.collection;

public class NullCollectionHost<K> implements CollectionHost<K> {
    // @formatter:off
    @Override public void adding(int index, K k, Object v) { }
    @Override public K removing(int index, Object o) { return null; }
    @Override public void clearing() { }
    @Override public void addingNulls(int index) { }
    @Override public boolean skipHostUpdate() { return false; }
    @Override public int getIteratorModificationCount() { return 0; }
    // @formatter:on
}
