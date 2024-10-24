package com.vladsch.flexmark.util.data;

interface MutableDataValueSetter<T> {

  MutableDataHolder set(MutableDataHolder dataHolder, T value);
}
