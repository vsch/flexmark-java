package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.mappers.CharMapper;

/**
 * A CharSequence that maps characters according to CharMapper
 */
public interface MappedSequence<T extends CharSequence> extends CharSequence {
    CharMapper getCharMapper();
    T getCharSequence();
}
