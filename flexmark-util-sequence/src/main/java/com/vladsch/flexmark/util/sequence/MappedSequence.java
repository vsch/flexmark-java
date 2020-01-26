package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;

/**
 * A CharSequence that maps characters according to CharMapper
 */
public interface MappedSequence<T extends CharSequence> extends CharSequence {
    @NotNull CharMapper getCharMapper();
    @NotNull T getCharSequence();
}
