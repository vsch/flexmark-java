package com.vladsch.flexmark.internal.util;

public interface MutableDataHolder extends DataHolder {
    <T> MutableDataHolder set(DataKey<T> key, T value);
}
