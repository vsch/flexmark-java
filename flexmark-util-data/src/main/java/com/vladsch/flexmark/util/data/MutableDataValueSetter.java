package com.vladsch.flexmark.util.data;


public interface MutableDataValueSetter<T> {
    MutableDataHolder set(MutableDataHolder dataHolder, T value);
}
